package com.sysdt.lock.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sysdt.lock.dao.EmailMapper;
import com.sysdt.lock.model.Email;
import com.sysdt.lock.model.EmailExample;

@Service
@Transactional
public class EmailService {

	@Autowired
	private EmailMapper emailMapper;
	
	public List<Email> obtenerCorreosPorUsername(String username){
		EmailExample exEmail = new EmailExample();
		exEmail.createCriteria().andUsernameEqualTo(username);
		return emailMapper.selectByExample(exEmail);
	}
	
	public void insertarEmail(String email, String username){
		Email email2 = new Email();
		email2.setDireccion(email.trim());
		email2.setUsername(username.trim());
		emailMapper.insert(email2);
	}
	
	public void eliminarEmailsPorUsername(String username){
		EmailExample exEmail = new EmailExample();
		exEmail.createCriteria().andUsernameEqualTo(username);
		emailMapper.deleteByExample(exEmail);
	}
	
}
