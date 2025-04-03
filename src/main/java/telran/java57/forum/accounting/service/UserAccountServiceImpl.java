package telran.java57.forum.accounting.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import telran.java57.forum.accounting.dao.UserAccountRepository;
import telran.java57.forum.accounting.dto.RolesDto;
import telran.java57.forum.accounting.dto.UpdateUserDto;
import telran.java57.forum.accounting.dto.UserDto;
import telran.java57.forum.accounting.dto.UserRegisterDto;
import telran.java57.forum.accounting.dto.exception.UserAccountNotFoundException;
import telran.java57.forum.accounting.model.UserAccount;

@Service
@RequiredArgsConstructor
public class UserAccountServiceImpl implements UserAccountService {

    final UserAccountRepository userAccountRepository;
    final ModelMapper modelMapper;

    @Override
    public UserDto register(UserRegisterDto userRegisterDto) {
        UserAccount userAccount = new UserAccount(userRegisterDto.getLogin(),
                userRegisterDto.getPassword(),userRegisterDto.getFirstName(),userRegisterDto.getLastName());
        userAccount = userAccountRepository.save(userAccount);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public UserDto getUser(String name) {
        UserAccount userAccount = userAccountRepository.findById(name).orElseThrow(() -> new UserAccountNotFoundException(name));
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public void changePassword(String name, String newPassword) {
        UserAccount userAccount = userAccountRepository.findById(name).orElseThrow(() -> new UserAccountNotFoundException(name));
        if (newPassword != null && !newPassword.equals(userAccount.getPassword())) {
            userAccount.setPassword(newPassword);
            userAccountRepository.save(userAccount);
        }
    }

    @Override
    public UserDto removeUser(String login) {
        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(() -> new UserAccountNotFoundException(login));
        userAccountRepository.deleteById(login);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public UserDto updateUser(String login, UpdateUserDto updateUserDto) {
        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(() -> new UserAccountNotFoundException(login));
        if(updateUserDto.getFirstName() != null) {
            userAccount.setFirstName(updateUserDto.getFirstName());
        }
        if (updateUserDto.getLastName() != null) {
            userAccount.setLastName(updateUserDto.getLastName());
        }
        userAccount = userAccountRepository.save(userAccount);
        return modelMapper.map(userAccount, UserDto.class);
    }

    @Override
    public RolesDto changeRolesList(String login, String role, boolean isAddRole) {
        UserAccount userAccount = userAccountRepository.findById(login).orElseThrow(() -> new UserAccountNotFoundException(login));
        if (isAddRole) {
            userAccount.addRole(role);
        } else {
            userAccount.removeRole(role);
        }
        userAccount = userAccountRepository.save(userAccount);
        return modelMapper.map(userAccount, RolesDto.class);
    }
}
