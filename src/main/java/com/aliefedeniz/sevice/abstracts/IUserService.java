package com.aliefedeniz.sevice.abstracts;

import com.aliefedeniz.core.results.Result;
import com.aliefedeniz.core.results.ResultData;
import com.aliefedeniz.dto.request.UserSaveRequest;
import com.aliefedeniz.dto.request.UserUpdateRequest;
import com.aliefedeniz.dto.response.UserResponse;

import java.util.List;

public interface IUserService {

    ResultData<UserResponse> saveUser(UserSaveRequest userSaveRequest);
    ResultData<UserResponse> updateUser(UserUpdateRequest userUpdateRequest);
    ResultData<List<UserResponse>> findByName(String name);
    ResultData<UserResponse> findByUserId(Long id);
    ResultData<List<UserResponse>> findAll();
    Result deleteUser(Long id);
}
