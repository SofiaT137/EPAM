package com.epam.jwd.service.converter;

import com.epam.jwd.Dao.model.AbstractEntity;
import com.epam.jwd.service.dto.AbstractDto;

/**
 * The converter
 * @param <T> AbstractEntity
 * @param <U> AbstractDto
 * @param <K> Integer
 */
public interface Converter<T extends AbstractEntity<K>, U extends AbstractDto<K>,K> {
        /**
         * Convert AbstractDto Object into AbstractEntity Object
         * @param value AbstractDto Object
         * @return AbstractEntity Object
         */
        T convert(U value);

        /**
         * Convert  AbstractEntity Object into AbstractDto Object
         * @param value AbstractEntity Object
         * @return AbstractDto Object
         */
        U convert(T value);
}
