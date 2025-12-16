package com.jobportal.backend.Service.Impl;

import com.jobportal.backend.Dto.ReferenceRequest;
import com.jobportal.backend.Entity.Address;
import com.jobportal.backend.Exception.ResourceNotFoundException;
import com.jobportal.backend.Repository.AddressRepo;
import com.jobportal.backend.Service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepo;

    @Override
    public List<Address> getAll() {
        return addressRepo.findAllByOrderByNameAsc();
    }

    @Override
    public Address getById(String id) {
        return addressRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy địa chỉ với ID: " + id));
    }

    @Override
    public Address create(ReferenceRequest request) {
        // Kiểm tra ID đã tồn tại chưa
        if (addressRepo.findById(request.getId()).isPresent()) {
            throw new IllegalArgumentException("ID địa chỉ đã tồn tại: " + request.getId());
        }

        // Kiểm tra tên đã tồn tại chưa
        if (addressRepo.findByName(request.getName()).isPresent()) {
            throw new IllegalArgumentException("Tên địa chỉ đã tồn tại: " + request.getName());
        }

        Address address = new Address();
        address.setId(request.getId());
        address.setName(request.getName());

        return addressRepo.save(address);
    }

    @Override
    public Address update(String id, ReferenceRequest request) {
        Address address = getById(id);

        // Kiểm tra tên mới có trùng với địa chỉ khác không
        if (!address.getName().equals(request.getName())) {
            if (addressRepo.findByName(request.getName()).isPresent()) {
                throw new IllegalArgumentException("Tên địa chỉ đã tồn tại: " + request.getName());
            }
        }

        address.setName(request.getName());
        return addressRepo.save(address);
    }

    @Override
    public void delete(String id) {
        Address address = getById(id);
        addressRepo.delete(address);
    }
}