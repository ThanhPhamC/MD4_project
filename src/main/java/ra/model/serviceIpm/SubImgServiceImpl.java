package ra.model.serviceIpm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ra.model.entity.SubImg;
import ra.model.repository.SubImgRepository;
import ra.model.service.SubImgService;
@Service
public class SubImgServiceImpl implements SubImgService<SubImg,Integer> {
    @Autowired
    private SubImgRepository subRepo;
    @Override
    public Page<SubImg> findByName(String name, Pageable pageable) {
        return subRepo.findByImgLinkContaining(name,pageable);
    }

    @Override
    public SubImg saveOrUpdate(SubImg subImg) {
        return subRepo.save(subImg);
    }

    @Override
    public void delete(Integer id) {
        subRepo.deleteById(id);
    }

    @Override
    public SubImg findById(Integer id) {
        return subRepo.findById(id).get();
    }

    @Override
    public Page<SubImg> getPagging(Pageable pageable) {
        return subRepo.findAll(pageable);
    }
}
