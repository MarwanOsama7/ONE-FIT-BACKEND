package com.global.ProjectManagement.Repository;

import java.util.Optional;

import com.global.ProjectManagement.Base.Repository.BaseRepository;
import com.global.ProjectManagement.Entity.PromoCode;

public interface PromoCodeRepository extends BaseRepository<PromoCode, Long> {
	
    Optional<PromoCode> findByPromoCode(String code);

}
