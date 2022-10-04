package imgscrap.repository;

import imgscrap.vo.TbImgScrapInfoVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImgScrapRepository extends JpaRepository<TbImgScrapInfoVo, Long> {
}
