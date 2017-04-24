package com.github.karina_denisevich.app.services;

import com.github.karina_denisevich.app.dao.AppDao;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import({
        AppDao.class
})
public class ApServices {

}
