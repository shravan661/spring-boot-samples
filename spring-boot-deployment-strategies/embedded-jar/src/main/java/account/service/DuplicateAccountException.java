package account.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Duplicate Account")  // 409
public class DuplicateAccountException extends RuntimeException {

    private static final long serialVersionUID = 8158414276763984030L;

    public DuplicateAccountException(String msg) {
        super(msg);
    }
}
