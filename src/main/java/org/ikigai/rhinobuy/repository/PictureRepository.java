package org.ikigai.rhinobuy.repository;

import org.ikigai.rhinobuy.domain.Picture;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Picture entity.
 */
@SuppressWarnings("unused")
public interface PictureRepository extends JpaRepository<Picture,Long> {

}
