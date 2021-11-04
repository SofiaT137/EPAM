package com.epam.jwd.service.conventer.api;

import com.epam.jwd.repository.model.AbstractEntity;
import com.epam.jwd.service.dto.AbstractDto;

public interface Conventer <T extends AbstractEntity<K>, U extends AbstractDto<K>,K> {
        T convert(U value);
        U convert(T value);
}
