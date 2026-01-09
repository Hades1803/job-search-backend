package com.jobportal.backend.Service;

import com.jobportal.backend.Entity.Employer;
import com.jobportal.backend.Entity.Account;

public interface EmployerService {

    Employer createEmployer(Employer employer, Account currentAccount);

    Employer updateEmployer(Employer employer, Account currentAccount);

    Employer getMyEmployer(Account currentAccount);
}
