package com.alex.asset.inventory.service;

import com.alex.asset.configure.domain.Branch;
import com.alex.asset.configure.repo.BranchRepo;
import com.alex.asset.exceptions.shared.ObjectAlreadyExistException;
import com.alex.asset.exceptions.shared.ResourceNotFoundException;
import com.alex.asset.inventory.domain.Inventory;
import com.alex.asset.inventory.domain.event.Event;
import com.alex.asset.inventory.domain.event.ScannedProduct;
import com.alex.asset.inventory.mapper.InventoryMapper;
import com.alex.asset.inventory.repo.EventRepo;
import com.alex.asset.inventory.repo.InventoryRepo;
import com.alex.asset.inventory.repo.ScannedProductRepo;
import com.alex.asset.logs.LogService;
import com.alex.asset.logs.domain.Action;
import com.alex.asset.logs.domain.Section;
import com.alex.asset.notification.NotificationService;
import com.alex.asset.product.domain.Product;
import com.alex.asset.product.repo.ProductRepo;
import com.alex.asset.security.domain.User;
import com.alex.asset.security.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DBSnapshotProvider {

    private final String TAG = "DB_SNAPSHOT_PROVIDER - ";
    private final UserRepo userRepo;
    private final BranchRepo branchRepo;
    private final ProductRepo productRepo;
    private final LogService logService;
    private final ScannedProductRepo scannedProductRepo;
    private final EventRepo eventRepo;
    private final InventoryRepo inventoryRepo;


    @SneakyThrows
    public void createEventsForInventory(Long userId) {
        log.info(TAG + "createEventsForInventory");
        User user = userRepo.getUser(userId);
        List<Branch> branches = branchRepo.getActive();
        for (Branch branch : branches) {
            createEventForBranch(user, branch);
        }
    }

    @SneakyThrows
    private void createEventForBranch(User user, Branch branch) {
        createEventForBranch(user, branch, "Default Info");
    }

    @SneakyThrows
    private Event createEventForBranch(User user, Branch branch, String info) {
        log.info(TAG + "createEventForBranch");
        Inventory inventory = inventoryRepo.getCurrentInventory(LocalDate.now())
                .orElseThrow(() -> new ResourceNotFoundException("No active inventory now"));

        if (eventRepo.existsByBranchAndInventory(branch, inventory))
            throw new ObjectAlreadyExistException("Event for this branch already exists");

        Event event = new Event();
        event.setActive(true);
        event.setInventory(inventory);
        event.setUser(user);
        event.setBranch(branch);
        event.setInfo(info);
        Event eventFrommDB = eventRepo.save(event);
        logService.addLog(user.getId(), Action.CREATE, Section.EVENT, info);
        createDBSnapshotForEvent(eventFrommDB);
        return event;
    }

    private void createDBSnapshotForEvent(Event event) {
        log.info(TAG + "createDBSnapshotForEvent");
        Branch branch = event.getBranch();
        List<Product> products = productRepo.findAllByBranch(branch);
        for (Product product : products) {
            ScannedProduct scannedProduct = new ScannedProduct().toBuilder()
                    .product(product).event(event).isScanned(false).build();
            scannedProductRepo.save(scannedProduct);
        }
    }
}
