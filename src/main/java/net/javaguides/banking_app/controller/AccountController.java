package net.javaguides.banking_app.controller;

import net.javaguides.banking_app.dto.AccountDto;
import net.javaguides.banking_app.service.AccountService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/acconts")
public class AccountController {

    private AccountService accountService;
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    //add account Restapi
    @PostMapping
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto) {
        return new ResponseEntity<>(accountService.createAccount(accountDto), HttpStatus.CREATED);
    }

    //get account restapi
    @GetMapping("/{id}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable Long id) {
        AccountDto accountDto = accountService.getAccountById(id);
        return ResponseEntity.ok(accountDto);
    }

    //deposit RESTAPI
    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto>deposit(@PathVariable Long id,
                                          @RequestBody  Map<String, Double>request){

        Double amount = request.get("amount");
         AccountDto accountdto = accountService.deposit(id,amount);
        return ResponseEntity.ok(accountdto);
    }

    //withdraw RESTAPI
    @PutMapping( "/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id,
                                              @RequestBody Map<String, Double>request){
        double amount = request.get("amount");
        AccountDto accountdto = accountService.withdraw(id,amount);
        return ResponseEntity.ok(accountdto);
    }

    //Get all accounts restapi
    @GetMapping
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accounts = accountService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    //delete account restapi
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
        return ResponseEntity.ok("Account deleted");
    }

}
