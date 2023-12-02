package cedeladela.hookabook.service;

import cedeladela.hookabook.entity.HbUser;
import cedeladela.hookabook.repository.HbUserRepository;
import exception.hbuser.HbUserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HbUserService {

    private final HbUserRepository hbUserRepository;

    @Autowired
    public HbUserService(HbUserRepository hbUserRepository) {
        this.hbUserRepository = hbUserRepository;
    }

    public List<HbUser> getAll() {
        return (List<HbUser>) hbUserRepository.findAll();
    }

    public HbUser getById(Long id) {
        Optional<HbUser> userOptional = hbUserRepository.findById(id);
        if (userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new HbUserNotFoundException(id);
        }
    }

    public HbUser create(HbUser hbUser) {
        return hbUserRepository.save(hbUser);
    }

    public HbUser update(HbUser hbUser) {
        Optional<HbUser> existingUserOptional = hbUserRepository.findById(hbUser.getId());
        if (existingUserOptional.isPresent()) {
            HbUser existingUser = existingUserOptional.get();

            existingUser.setUsername(hbUser.getUsername());
            existingUser.setPassword(hbUser.getPassword());
            existingUser.setEmail(hbUser.getEmail());
            existingUser.setFirstName(hbUser.getFirstName());
            existingUser.setLastName(hbUser.getLastName());
            existingUser.setIsApproved(hbUser.getIsApproved());

            return hbUserRepository.save(existingUser);
        } else {
            throw new HbUserNotFoundException(hbUser.getId());
        }
    }

    public void delete(Long id) {
        Optional<HbUser> existingUserOptional = hbUserRepository.findById(id);
        if (existingUserOptional.isPresent()) {
            hbUserRepository.deleteById(id);
        } else {
            throw new HbUserNotFoundException(id);
        }
    }
}
