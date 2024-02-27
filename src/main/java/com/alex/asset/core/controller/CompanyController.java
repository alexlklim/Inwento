package com.alex.asset.core.controller;

import com.alex.asset.core.dto.CompanyDto;
import com.alex.asset.core.dto.simple.DtoName;
import com.alex.asset.core.service.CompanyService;
import com.alex.asset.security.domain.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j @RestController @CrossOrigin
@RequiredArgsConstructor @RequestMapping("/api/core/company")
public class CompanyController {
    private final String TAG = "COMPANY_CONTROLLER - ";
    private final CompanyService companyService;

    @GetMapping
    public ResponseEntity<CompanyDto> getInfoAboutCompany() {
        return new ResponseEntity<>(companyService.getInfoAboutCompany(), HttpStatus.OK);
    }

    @PutMapping  @Secured("ROLE_ADMIN")
    public ResponseEntity<CompanyDto> updateCompany(@RequestBody CompanyDto dto) {
        log.info(TAG + "try to update company ");
        return new ResponseEntity<>(companyService.updateCompany(dto), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getInfoAboutEmployee(@PathVariable("id") Long id) {
        return new ResponseEntity<>(companyService.getInfoAboutEmpById(id), HttpStatus.OK);
    }

    @GetMapping("/emp")  @Secured("ROLE_ADMIN")
    public ResponseEntity<List<UserDto>> getAllEmployee() {
        return new ResponseEntity<>(companyService.getAllEmployee(), HttpStatus.OK);
    }

    @PutMapping("/emp")  @Secured("ROLE_ADMIN")
    public ResponseEntity<HttpStatus> makeUserActive(@RequestBody DtoName dto) {
        log.info(TAG + "Try to make user inactive ");
        companyService.makeUserActive(dto.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/emp")  @Secured("ROLE_ADMIN")
    public ResponseEntity<HttpStatus> makeUserInactive(@RequestBody DtoName dto) {
        log.info(TAG + "Try to make user inactive ");
        companyService.makeUserInactive(dto.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/emp/del") @Secured("ROLE_ADMIN")
    public ResponseEntity<HttpStatus> deleteUser(@RequestBody DtoName dto) {
        log.info(TAG + "Try to delete user");
        companyService.deleteUser(dto.getName());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
