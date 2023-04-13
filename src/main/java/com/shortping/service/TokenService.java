package com.shortping.service;


import com.shortping.repository.MemberRepository;
import com.shortping.repository.SellerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TokenService {

    private final MemberRepository memberRepo;

    private final SellerRepository sellerRepo;

    public boolean isMemberValid(String email) {
        if (memberRepo.findByMemberEmail(email).isEmpty()
                && sellerRepo.findBySellerEmail(email).isEmpty()) {
            return false;
        }
        return true;
    }
}
