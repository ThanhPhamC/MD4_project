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
import ra.dto.request.ColorModel;
import ra.dto.response.MessageResponse;
import ra.model.entity.Catalog;
import ra.model.entity.Color;
import ra.model.service.ColorService;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/api/v1/color")
@AllArgsConstructor
public class ColorController {
    private ColorService colorService;
    @GetMapping("/getPaggingAndSortByName")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, Object>> getPaggingAndSortByName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "4") int size,
            @RequestParam String direction) {
        Sort.Order order;
        if (direction.equals("asc")) {
            order = new Sort.Order(Sort.Direction.ASC, "colorName");
        } else {
            order = new Sort.Order(Sort.Direction.DESC, "colorName");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));
        Page<Color> pageColor = colorService.getPagging(pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("catalogs", pageColor.getContent());
        data.put("total", pageColor.getSize());
        data.put("totalItems", pageColor.getTotalElements());
        data.put("totalPages", pageColor.getTotalPages());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
    @PostMapping("/creatNew")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> creatNew(@RequestBody ColorModel colorModel) {
        try {
            Color color = new Color();
            color.setColorName(colorModel.getColorName());
            color.setColorHex(colorModel.getColorHex());
            color.setColorStatus(true);
            colorService.saveOrUpdate(color);
            return ResponseEntity.ok(colorService.saveOrUpdate(color));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new MessageResponse("Creat new color error"));
        }
    }
    @PutMapping("/updateColor")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> updateColor(@RequestBody Color color) {
        try {
            colorService.saveOrUpdate(color);
            return ResponseEntity.ok(colorService.saveOrUpdate(color));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new MessageResponse("Update color error"));
        }
    }
    @DeleteMapping("/deleteColor")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<?> deleteColor(int colorId) {
        try {
            Color color = (Color) colorService.findById(colorId);
            color.setColorStatus(false);
            colorService.saveOrUpdate(color);
            return ResponseEntity.ok(colorService.saveOrUpdate(color));
        } catch (Exception exception) {
            return ResponseEntity.badRequest().body(new MessageResponse("Delete color error"));
        }
    }
    @GetMapping("/findByName")
    @PreAuthorize(" hasRole('MODERATOR') or hasRole('ADMIN')")
    ResponseEntity<Map<String, Object>> findByName(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "1") int size,
            @RequestParam String direction,
            @RequestParam String colorName) {
        Sort.Order order;
        if (direction.equals("asc")) {
            order = new Sort.Order(Sort.Direction.ASC, "colorName");
        } else {
            order = new Sort.Order(Sort.Direction.DESC, "colorName");
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(order));
        Page<Color> pageColor = colorService.findByName(colorName,pageable);
        Map<String, Object> data = new HashMap<>();
        data.put("colos", pageColor.getContent());
        data.put("total", pageColor.getSize());
        data.put("totalItems", pageColor.getTotalElements());
        data.put("totalPages", pageColor.getTotalPages());
        return new ResponseEntity<>(data, HttpStatus.OK);
    }
}
