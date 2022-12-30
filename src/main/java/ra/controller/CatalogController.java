package ra.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ra.dto.request.CatalogModel;
import ra.dto.response.CatalogDto;
import ra.model.entity.Catalog;
import ra.model.service.CatalogService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/v1/catalog") @AllArgsConstructor
public class CatalogController {
   private CatalogService catalogService;
    @GetMapping("/getPaggingAndSortByName")
    public ResponseEntity<Map<String,Object>> getPaggingAndSortByName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam String direction){
        Sort.Order order;
        if (direction.equals("asc")){
            order=new Sort.Order(Sort.Direction.ASC,"catalogName");
        }else{
            order=new Sort.Order(Sort.Direction.DESC,"catalogName");
        }
        Pageable pageable = PageRequest.of(page,size,Sort.by(order));
        Page<Catalog> pageCat = catalogService.getPagging(pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("catalogs",pageCat.getContent());
        data.put("total",pageCat.getSize());
        data.put("totalItems",pageCat.getTotalElements());
        data.put("totalPages",pageCat.getTotalPages());
        return  new ResponseEntity<>(data, HttpStatus.OK);
    }
    @GetMapping("/searchByName")
    public ResponseEntity<Map<String,Object>> searchByName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam String catalogName){
        Pageable pageable = PageRequest.of(page,size);
        Page<Catalog> pageCat = catalogService.findByName(catalogName,pageable);
        Map<String,Object> data = new HashMap<>();
        data.put("catalogs",pageCat.getContent());
        data.put("total",pageCat.getSize());
        data.put("totalItems",pageCat.getTotalElements());
        data.put("totalPages",pageCat.getTotalPages());
        return  new ResponseEntity<>(data, HttpStatus.OK);
    }
    @PostMapping("/creatNew")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public CatalogDto creatNeW(@RequestBody CatalogModel catalogModel) {
        Catalog catalog = new Catalog(catalogModel);
        Catalog cat = (Catalog) catalogService.saveOrUpdate(catalog);
        CatalogDto cdt = new CatalogDto(cat);
        return cdt;
    }
    @PutMapping("/update")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public CatalogDto updateCatalog(@RequestBody CatalogDto catalogDto) {
        Catalog cat = new Catalog(catalogDto);
        CatalogDto cdt = (CatalogDto) catalogService.saveOrUpdate(cat);
        return cdt;
    }
    @GetMapping("findChildById")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public List<CatalogDto> findChildById(int catalogId) {
        List<Catalog> lists = catalogService.findChildById(catalogId);
        List<CatalogDto> dtoList = lists.stream()
                .map(item -> new CatalogDto(item)).collect(Collectors.toList());
        return dtoList;
    }
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCatalog(int catalogId){
        List<Catalog> lists = catalogService.findChildById(catalogId);
        for (Catalog cat:lists) {
            cat.setCatalogStatus(false);
            catalogService.saveOrUpdate(cat);
        }
        return ResponseEntity.ok("Successfully");
    }
}
