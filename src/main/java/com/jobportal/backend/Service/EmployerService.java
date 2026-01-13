package com.jobportal.backend.Service;

import com.jobportal.backend.Entity.Employer;
import com.jobportal.backend.Entity.Account;

import com.jobportal.backend.Dto.EmployerProfileRequest;

import java.io.IOException;

public interface EmployerService {

    //Employer createEmployer(Employer employer, Account currentAccount);

    Employer updateEmployer(EmployerProfileRequest request) throws IOException;

    Employer getMyEmployer();
}
