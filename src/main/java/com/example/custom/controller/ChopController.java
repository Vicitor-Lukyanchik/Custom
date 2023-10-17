package com.example.custom.controller;

import com.example.custom.converter.SortParametersConverter;
import com.example.custom.dto.IndexDto;
import com.example.custom.entity.Chop;
import com.example.custom.service.ChopService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequiredArgsConstructor
@RequestMapping("/chops")
public class ChopController {

    private final ChopService chopService;
    private final SortParametersConverter sortConverter;

    @GetMapping
    public String index(Model model, @RequestParam("offset") Optional<Integer> offset,
                        @RequestParam("pageSize") Optional<Integer> pageSize,
                        @RequestParam("isId") Optional<Boolean> isId,
                        @RequestParam("isIdDesc") Optional<Boolean> isIdDesc,
                        @RequestParam("isName") Optional<Boolean> isName,
                        @RequestParam("isNameDesc") Optional<Boolean> isNameDesc,
                        @RequestParam("number") Optional<String> number) {
        Page<Chop> chopPages = chopService.findAll(IndexDto.builder()
                .offset(offset.orElse(0))
                .name(number.orElse(""))
                .pageSize(pageSize.orElse(0))
                .sortParameters(sortConverter.buildInterestSortParameters(isId, isIdDesc, isNameDesc, isName))
                .build());

        model.addAttribute("chopsPages", chopPages);
        model.addAttribute("chops", chopPages.getContent());
        model.addAttribute("size", pageSize.orElse(5));
        model.addAttribute("offset", offset.orElse(0));

        if (chopPages.getTotalPages() > 0) {
            model.addAttribute("pageNumbers",
                    IntStream.rangeClosed(1, chopPages.getTotalPages()).boxed().collect(Collectors.toList()));
        }
        return "chop/index";
    }

    @GetMapping("/{number}")
    public String show(@PathVariable("number") String number, Model model) {
        model.addAttribute("chop", chopService.findByNumber(number).get());
        return "chop/show";
    }
}
