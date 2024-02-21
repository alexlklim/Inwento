package com.alex.asset.core.service;


import com.alex.asset.core.domain.Company;
import com.alex.asset.core.domain.fields.Branch;
import com.alex.asset.core.domain.fields.MPK;
import com.alex.asset.core.domain.fields.Producer;
import com.alex.asset.core.domain.fields.Supplier;
import com.alex.asset.core.domain.fields.constants.AssetStatus;
import com.alex.asset.core.domain.fields.constants.KST;
import com.alex.asset.core.domain.fields.constants.Unit;
import com.alex.asset.core.dto.SimpleDto;
import com.alex.asset.core.repo.CompanyRepo;
import com.alex.asset.core.repo.product.BranchRepo;
import com.alex.asset.core.repo.product.MpkRepo;
import com.alex.asset.core.repo.product.ProducerRepo;
import com.alex.asset.core.repo.product.SupplierRepo;
import com.alex.asset.core.repo.product.constatns.AssetStatusRepo;
import com.alex.asset.core.repo.product.constatns.KstRepo;
import com.alex.asset.core.repo.product.constatns.UnitRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class FieldService {
    private final String TAG = "FIELD_CONTROLLER - ";

    private final BranchRepo branchRepo;
    private final SupplierRepo supplierRepo;
    private final ProducerRepo producerRepo;
    private final MpkRepo mpkRepo;
    private final CompanyRepo companyRepo;

    private final AssetStatusRepo assetStatusRepo;
    private final KstRepo kstRepo;
    private final UnitRepo unitRepo;



    public List<String> getBranchNames(Company company) {
        log.info(TAG + "get branches name for company {}", company.getCompany());
        List<String> list = new ArrayList<>();
        for (Branch branch : getBranches(company)) {
            list.add(branch.getBranch());
        }
        return list;
    }

    public List<String> getSupplierNames(Company company) {
        log.info(TAG + "get suppliers name for company {}", company.getCompany());
        List<String> list = new ArrayList<>();
        for (Supplier supplier : getSuppliers(company)) {
            list.add(supplier.getSupplier());
        }
        return list;
    }

    public List<String> getProducerNames(Company company) {
        log.info(TAG + "get producers name for company {}", company.getCompany());
        List<String> list = new ArrayList<>();
        for (Producer producer : getProducers(company)) {
            list.add(producer.getProducer());
        }
        return list;
    }
    public List<String> getMPKNames(Company company) {
        log.info(TAG + "get MPKs name for company {}", company.getCompany());
        List<String> list = new ArrayList<>();
        for (MPK mpk: getMPKs(company)) {
            list.add(mpk.getMpk());
        }
        return list;
    }

    private List<Branch> getBranches(Company company) {
        return branchRepo.findByActiveTrueAndCompany(company);
    }

    private List<Supplier> getSuppliers(Company company) {
        return supplierRepo.findByActiveTrueAndCompany(company);
    }

    private List<Producer> getProducers(Company company) {
        return producerRepo.findByActiveTrueAndCompany(company);
    }

    private List<MPK> getMPKs(Company company) {
        return mpkRepo.findByActiveTrueAndCompany(company);
    }




    @Modifying
    public boolean updateKst(List<String> list, UUID comapnyUUID) {
        log.info(TAG + "Try to update kst: {} for company {}", list, comapnyUUID);
        Company company = companyRepo.findById(comapnyUUID).orElse(null);
        if (company == null) {
            log.error(TAG + "Company with id: {} not found", comapnyUUID);
            return false;
        }
        List<KST> ksts = company.getKsts();
        ksts.removeIf(kst -> !list.contains(kst.getKst()));
        companyRepo.save(company);
        return true;
    }


    public boolean updateAssetStatuses(List<String> list, UUID comapnyUUID) {
        log.info(TAG + "Try to update asset status: {} for company {}", list, comapnyUUID);
        Company company = companyRepo.findById(comapnyUUID).orElse(null);
        if (company == null) {
            log.error(TAG + "Company with id: {} not found", comapnyUUID);
            return false;
        }
        List<AssetStatus> assetStatus = company.getAssetStatus();
        assetStatus.removeIf(status -> !list.contains(status.getAssetStatus()));
        companyRepo.save(company);
        return true;
    }

    public boolean updateUnits(List<String> list, UUID comapnyUUID) {
        log.info(TAG + "Try to update units: {} for company {}", list, comapnyUUID);
        Company company = companyRepo.findById(comapnyUUID).orElse(null);
        if (company == null) {
            log.error(TAG + "Company with id: {} not found", comapnyUUID);
            return false;
        }
        List<Unit> units = company.getUnits();
        units.removeIf(unit -> !list.contains(unit.getUnit()));
        companyRepo.save(company);
        return true;
    }




    public boolean addBranches(List<String> list, UUID comapnyUUID) {
        log.info(TAG + "Try to add Branches: {} for company {}", list, comapnyUUID);
        Company company = companyRepo.findById(comapnyUUID).orElse(null);
        if (company == null) {
            log.error(TAG + "Company with id: {} not found", comapnyUUID);
            return false;
        }
        for (String name: list){
            Optional<Branch> optional =  branchRepo.findByBranchAndCompany(name, company);
            if (optional.isEmpty()){
                log.info(TAG + "Adding mpk {} to company {}", name, company.getCompany());
                Branch entity = new Branch(name, company);
                branchRepo.save(entity);
            } else {
                if (!optional.get().isActive()){
                    log.info(TAG + "Make active branch {} for company {}", name, company.getCompany());
                    Branch entity = optional.get();
                    entity.setActive(true);
                    branchRepo.save(entity);
                }
            }
        }
        return  true;
    }

    public boolean addMPKs(List<String> list, UUID comapnyUUID) {
        log.info(TAG + "Try to add MPKS: {} for company {}", list, comapnyUUID);
        Company company = companyRepo.findById(comapnyUUID).orElse(null);
        if (company == null) {
            log.error(TAG + "Company with id: {} not found", comapnyUUID);
            return false;
        }
        for (String name: list){
            Optional<MPK> optionalMPK =  mpkRepo.findByMpkAndCompany(name, company);
            if (optionalMPK.isEmpty()){
                log.info(TAG + "Adding mpk {} to company {}", name, company.getCompany());
                MPK mpk = new MPK(name, company);
                mpkRepo.save(mpk);
            } else {
                if (!optionalMPK.get().isActive()){
                    MPK mpkEntity = optionalMPK.get();
                    mpkEntity.setActive(true);
                    mpkRepo.save(mpkEntity);
                }
            }
        }
        return  true;
    }



    public boolean addSuppliers(List<String> list, UUID comapnyUUID) {
        log.info(TAG + "Try to add Suppliers: {} for company {}", list, comapnyUUID);
        Company company = companyRepo.findById(comapnyUUID).orElse(null);
        if (company == null) {
            log.error(TAG + "Company with id: {} not found", comapnyUUID);
            return false;
        }
        for (String name: list){
            Optional<Supplier> optional =  supplierRepo.findBySupplierAndCompany(name, company);
            if (optional.isEmpty()){
                log.info(TAG + "Adding suppliers {} to company {}", name, company.getCompany());
                Supplier entity = new Supplier(name, company);
                supplierRepo.save(entity);
            } else {
                if (!optional.get().isActive()){
                    log.info(TAG + "Make active supplier {} for company {}", name, company.getCompany());
                    Supplier entity = optional.get();
                    entity.setActive(true);
                    supplierRepo.save(entity);
                }
            }
        }
        return  true;
    }

    public boolean addProducers(List<String> list, UUID comapnyUUID) {
        log.info(TAG + "Try to add Producers: {} for company {}", list, comapnyUUID);
        Company company = companyRepo.findById(comapnyUUID).orElse(null);
        if (company == null) {
            log.error(TAG + "Company with id: {} not found", comapnyUUID);
            return false;
        }
        for (String name: list){
            Optional<Producer> optional =  producerRepo.findByProducerAndCompany(name, company);
            if (optional.isEmpty()){
                log.info(TAG + "Adding producer {} to company {}", name, company.getCompany());
                Producer entity = new Producer(name, company);
                producerRepo.save(entity);
            } else {
                if (!optional.get().isActive()){
                    log.info(TAG + "Make active producer {} for company {}", name, company.getCompany());
                    Producer entity = optional.get();
                    entity.setActive(true);
                    producerRepo.save(entity);
                }
            }
        }
        return  true;
    }


    public boolean deleteBranch(SimpleDto dto, UUID comapnyUUID) {
        log.info(TAG + "Try to delete branch: {} for company {}", dto.getName(), comapnyUUID);
        Company company = companyRepo.findById(comapnyUUID).orElse(null);
        if (company == null) {
            log.error(TAG + "Company with id: {} not found", comapnyUUID);
            return false;
        }
        Optional<Branch> optional = branchRepo.findByBranchAndCompany(dto.getName(), company);
        if (optional.isPresent()){

            Branch branch = optional.get();
            branch.setActive(false);
            log.info(TAG + "Delete branch: {} for company {}", dto.getName(), company.getCompany());
            branchRepo.save(branch);
            return true;
        }
        log.info(TAG + "Branch: {} not found", dto.getName());
        return false;
    }

    public boolean deleteSupplier(SimpleDto dto, UUID comapnyUUID) {
        log.info(TAG + "Try to delete supplier: {} for company {}", dto.getName(), comapnyUUID);
        Company company = companyRepo.findById(comapnyUUID).orElse(null);
        if (company == null) {
            log.error(TAG + "Company with id: {} not found", comapnyUUID);
            return false;
        }
        Optional<Supplier> optional = supplierRepo.findBySupplierAndCompany(dto.getName(), company);
        if (optional.isPresent()){
            Supplier entity = optional.get();
            entity.setActive(false);
            log.info(TAG + "Delete supplier: {} for company {}", dto.getName(), company.getCompany());
            supplierRepo.save(entity);
            return true;
        }
        log.info(TAG + "Supplier: {} not found", dto.getName());
        return false;
    }


    public boolean deleteProducer(SimpleDto dto, UUID comapnyUUID) {
        log.info(TAG + "Try to delete producer: {} for company {}", dto.getName(), comapnyUUID);
        Company company = companyRepo.findById(comapnyUUID).orElse(null);
        if (company == null) {
            log.error(TAG + "Company with id: {} not found", comapnyUUID);
            return false;
        }
        Optional<Producer> optional = producerRepo.findByProducerAndCompany(dto.getName(), company);
        if (optional.isPresent()){
            Producer entity = optional.get();
            entity.setActive(false);
            log.info(TAG + "Delete producer: {} for company {}", dto.getName(), company.getCompany());
            producerRepo.save(entity);
            return true;
        }
        log.info(TAG + "Producer: {} not found", dto.getName());
        return false;
    }

    public boolean deleteMPK(SimpleDto dto, UUID comapnyUUID) {
        log.info(TAG + "Try to delete MPK: {} for company {}", dto.getName(), comapnyUUID);
        Company company = companyRepo.findById(comapnyUUID).orElse(null);
        if (company == null) {
            log.error(TAG + "Company with id: {} not found", comapnyUUID);
            return false;
        }
        Optional<MPK> optional = mpkRepo.findByMpkAndCompany(dto.getName(), company);
        if (optional.isPresent()){
            MPK entity = optional.get();
            entity.setActive(false);
            log.info(TAG + "Delete MPK: {} for company {}", dto.getName(), company.getCompany());
            mpkRepo.save(entity);
            return true;
        }
        log.info(TAG + "MPK: {} not found", dto.getName());
        return false;
    }



    public AssetStatus getAssetStatus(String name){
        return assetStatusRepo.findByAssetStatus(name).orElse(null);
    }
    public KST getKST(String name){
        return kstRepo.findByKst(name).orElse(null);
    }
    public Unit getUnit(String name){
        return unitRepo.findByUnit(name).orElse(null);
    }



    public Branch getBranch(String name, Company company){
        return branchRepo.findByBranchAndCompany(name, company).orElse(null);
    }

    public Producer getProducer(String name, Company company){
        return producerRepo.findByProducerAndCompany(name, company).orElse(null);
    }

    public Supplier getSupplier(String name, Company company){
        return supplierRepo.findBySupplierAndCompany(name, company).orElse(null);
    }

    public MPK getMPK(String name, Company company){
        return mpkRepo.findByMpkAndCompany(name, company).orElse(null);
    }
}
