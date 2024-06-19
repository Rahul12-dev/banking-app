package net.javaguides.banking_app.service.impl;

import net.javaguides.banking_app.dto.AccountDto;
import net.javaguides.banking_app.entity.Account;
import net.javaguides.banking_app.mapper.AccountMapper;
import net.javaguides.banking_app.repository.AccountRepository;
import net.javaguides.banking_app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceimpl implements AccountService {

    private AccountRepository accountRepository;

    public AccountServiceimpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }
    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedaccount =accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedaccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, Double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));
        double total = account.getBalance() + amount;
        account.setBalance(total);
        Account savedaccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedaccount);
    }

    @Override
    public AccountDto withdraw(Long id, Double amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

        if(account.getBalance() < amount){
            throw new RuntimeException("Insufficient balance");
        }

        double total = account.getBalance() - amount ;
        account.setBalance(total);
        Account savedaccount = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedaccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return  accounts.stream().map((account)-> AccountMapper.mapToAccountDto(account))
                .collect(Collectors.toList());

    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account does not exist"));

        accountRepository.deleteById(id);
    }
}
