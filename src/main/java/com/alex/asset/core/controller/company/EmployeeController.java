package com.alex.asset.core.controller.company;

import com.alex.asset.core.dto.EmployeeDto;
import com.alex.asset.core.service.CompanyService;
import com.alex.asset.core.service.UserService;
import com.alex.asset.security.config.jwt.CustomPrincipal;
import com.alex.asset.security.domain.dto.UserDtoShort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@CrossOrigin
@RequiredArgsConstructor
@RequestMapping("/api/core/company/emp")
public class EmployeeController {
    private final String TAG = "EMPLOYEE_CONTROLLER - ";

    private final UserService userService;
    private final CompanyService companyService;

    @Secured({"ROLE_CLIENT", "ROLE_ADMIN"})
    @GetMapping
    public ResponseEntity<List<EmployeeDto>> getAllEmployee(Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.warn(TAG + "Try to get all employee");

        List<EmployeeDto> list = userService.getEmployeeListFromMyCompany(principal);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Secured({"ROLE_CLIENT", "ROLE_ADMIN"})
    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeById(
            @PathVariable("id") String id, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.warn(TAG + "Try to get employee by id");
        // if company id is not available return NOT_FOUND


        UserDtoShort userDto = userService.getEmployeeById(principal.getUserId(), principal);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }


    @Secured("ROLE_CLIENT")
    @PostMapping
    public ResponseEntity<?> addUserToEmployee(@RequestBody EmployeeDto dto, Authentication authentication){
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.warn(TAG + "Add user to employee by {} ", principal.getName());

        companyService.addUserToEmployee(dto, principal);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @Secured({"ROLE_CLIENT", "ROLE_ADMIN"})
    @DeleteMapping("/{uuid}")
    public ResponseEntity<HttpStatus> deleteEmpFromActiveEmp(
            @PathVariable("uuid") String id, Authentication authentication) {
        CustomPrincipal principal = (CustomPrincipal) authentication.getPrincipal();
        log.warn(TAG + "Try to delete user from active employee");

//        boolean result = userService.deleteEmpFromActiveEmp(UUID.fromString(id), principal);
//        if (!result){
//            log.error(TAG + "Employee wasn't deleted from active employee");
//            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
//        }
        log.info(TAG + "User {} was deleted from active employee", id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
}
