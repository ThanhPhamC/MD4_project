package ra.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ra.dto.request.ColorModel;
import ra.dto.request.SizeModel;
import ra.dto.response.MessageResponse;
import ra.model.entity.Color;
import ra.model.entity.Size;
import ra.model.service.ColorService;
import ra.model.service.SizeService;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/v1/size")
@AllArgsConstructor
public class SizeController {
    private SizeService sizeService;
    @GetMapping("/getPaggingAndSortByName")
    public ResponseEntity<Map<String, Object>> getPaggingAndSortByName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam String direction) {
        Sort.Order order;
        if (direction.equals("asc")) {
            order = new Sort.Order(Sort.Direction.ASC, "sizeName");
        } else {
            order = new Sort.Order(Sort.Direction.DESC, "sizeName");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));
        Page<Size> pageSize = sizeService.getPagging(pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("Sizes", pageSize.getContent());
        data.put("total", pageSize.getSize());
        data.put("totalItems", pageSize.getTotalElements());
        data.put("totalPages", pageSize.getTotalPages());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    @PostMapping("/creatNew")
    public ResponseEntity<?> creatNew(@RequestBody SizeModel sizeModel  ) {
        try {
            Size size = new Size();
            size.setSizeName(sizeModel.getSizeName());
            size.setSizeStatus(true);
            sizeService.saveOrUpdate(size);
            return ResponseEntity.ok(sizeService.saveOrUpdate(size));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new MessageResponse("Creat new Size error"));
        }
    }
    @PutMapping("/updateSize")
    public ResponseEntity<?> updateSize(@RequestBody Size size) {
        try {
            sizeService.saveOrUpdate(size);
            return ResponseEntity.ok(sizeService.saveOrUpdate(size));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new MessageResponse("Update size error"));
        }
    }
    @PutMapping("/deleteSize")
    public ResponseEntity<?> deleteSize(int sizeId) {
        try {
            Size size = (Size) sizeService.findById(sizeId);
           size.setSizeStatus(false);
            return ResponseEntity.ok(sizeService.saveOrUpdate(size));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new MessageResponse("Delete size error"));
        }
    }
    @GetMapping("/findByName")
    ResponseEntity<Map<String, Object>> findByName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size,
            @RequestParam String direction,
            @RequestParam String sizeName) {
        Sort.Order order;
        if (direction.equals("asc")) {
            order = new Sort.Order(Sort.Direction.ASC, "sizeName");
        } else {
            order = new Sort.Order(Sort.Direction.DESC, "sizeName");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));
        Page<Size> pageSize = sizeService.findByName(sizeName,pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("catalogs", pageSize.getContent());
        data.put("total", pageSize.getSize());
        data.put("totalItems", pageSize.getTotalElements());
        data.put("totalPages", pageSize.getTotalPages());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}

