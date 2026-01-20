package com.jobportal.backend.Service;

import com.jobportal.backend.Entity.Employer;
import com.jobportal.backend.Entity.Account;

import com.jobportal.backend.Dto.EmployerProfileRequest;

import java.io.IOException;
import java.util.List;

public interface EmployerService {

    //Employer createEmployer(Employer employer, Account currentAccount);

    Employer updateEmployer(EmployerProfileRequest request) throws IOException;

    Employer getMyEmployer();

    List<Employer> getAllEmployersForAdmin();
}
