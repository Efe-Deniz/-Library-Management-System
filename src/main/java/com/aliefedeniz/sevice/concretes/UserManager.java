package com.aliefedeniz.sevice.concretes;

import com.aliefedeniz.core.results.Result;
import com.aliefedeniz.core.results.ResultData;
import com.aliefedeniz.core.results.ResultInfo;
import com.aliefedeniz.dto.request.UserSaveRequest;
import com.aliefedeniz.dto.request.UserUpdateRequest;
import com.aliefedeniz.dto.response.UserResponse;
import com.aliefedeniz.entity.User;


import com.aliefedeniz.repository.UserRepository;
import com.aliefedeniz.sevice.abstracts.IUserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class UserManager implements IUserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public ResultData<UserResponse> saveUser(UserSaveRequest userSaveRequest) {
        if(userRepository.existByEmail(userSaveRequest.getEmail())) throw new IllegalArgumentException("Bu emaile kayıtlı kullanıcı vardır");

        User user = modelMapper.map(userSaveRequest,User.class);
        userRepository.save(user);
        UserResponse userResponse = modelMapper.map(user,UserResponse.class);
        return ResultInfo.created(userResponse);
    }

    @Override
    public ResultData<UserResponse> updateUser(UserUpdateRequest userUpdateRequest) {
        User existing = userRepository.findById(userUpdateRequest.getId())
                .orElseThrow(()->new EntityNotFoundException("Güncellenecek kullanıcı bulunamadı"));
        modelMapper.map(userUpdateRequest,existing);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        userRepository.save(existing);
        UserResponse userResponse = modelMapper.map(existing,UserResponse.class);

        return ResultInfo.success(userResponse);
    }

    @Override
    public ResultData<List<UserResponse>> findByName(String name) {
        List<User>userList=userRepository.findByNameContainingIgnoreCase(name);
        if(userList.isEmpty()) throw new EntityNotFoundException("bu isimde kullanıcı bulunamadı");
        List<UserResponse> userResponseList = userList.stream()
                .map(user -> modelMapper.map(user,UserResponse.class))
                .collect(Collectors.toList());
        return ResultInfo.success(userResponseList);
    }

    @Override
    public ResultData<UserResponse> findByUserId(Long id) {
        User existing = userRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Bu kullanıcı bulunamadı"));
        UserResponse userResponse = modelMapper.map(existing,UserResponse.class);

        return ResultInfo.success(userResponse);
    }

    @Override
    public ResultData<List<UserResponse>> findAll() {
        List<User> userList = userRepository.findAll();
        List<UserResponse> userResponseList = userList.stream()
                .map(user -> modelMapper.map(user,UserResponse.class))
                .collect(Collectors.toList());

        return ResultInfo.success(userResponseList);
    }

    @Override
    public Result deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Silinecek Kullanıcı bulunamadı"));
        return ResultInfo.okMessage("Kullanıcı başarıyla silindi");
    }
}
