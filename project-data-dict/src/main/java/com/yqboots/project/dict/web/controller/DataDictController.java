/*
 * Copyright 2015-2016 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yqboots.project.dict.web.controller;

import com.yqboots.project.dict.core.DataDict;
import com.yqboots.project.dict.core.DataDictExistsException;
import com.yqboots.project.dict.core.DataDictManager;
import com.yqboots.project.dict.web.form.FileUploadForm;
import com.yqboots.project.fss.web.util.FssWebUtils;
import com.yqboots.project.web.WebKeys;
import com.yqboots.project.web.form.SearchForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;

/**
 * The main Controller for DataDict.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/project/dict")
@SessionAttributes(names = {WebKeys.SEARCH_FORM})
public class DataDictController {
    private static final String REDIRECT_VIEW_PATH = "redirect:/project/dict";
    private static final String VIEW_HOME = "project/dict/index";
    private static final String VIEW_FORM = "project/dict/form";

    @Autowired
    @Qualifier("dataDictManager")
    private DataDictManager dataDictManager;

    @ModelAttribute(WebKeys.SEARCH_FORM)
    protected SearchForm<String> searchForm() {
        return new SearchForm<>();
    }

    @ModelAttribute("fileUploadForm")
    protected FileUploadForm fileUploadForm() {
        return new FileUploadForm();
    }

    @RequestMapping(method = {RequestMethod.GET, RequestMethod.POST})
    public String list(@ModelAttribute(WebKeys.SEARCH_FORM) final SearchForm<String> searchForm,
                       @PageableDefault final Pageable pageable, final ModelMap model) {
        model.addAttribute(WebKeys.PAGE, dataDictManager.getDataDicts(searchForm.getCriterion(), pageable));
        return VIEW_HOME;
    }

    @RequestMapping(params = {WebKeys.ACTION_NEW}, method = RequestMethod.GET)
    public String preAdd(final ModelMap model) {
        model.addAttribute(WebKeys.MODEL, new DataDict());
        return VIEW_FORM;
    }

    @RequestMapping(params = {WebKeys.ID, WebKeys.ACTION_UPDATE}, method = RequestMethod.GET)
    public String preUpdate(@RequestParam final Long id, final ModelMap model) {
        model.addAttribute(WebKeys.MODEL, dataDictManager.getDataDict(id));
        return VIEW_FORM;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String update(@Valid @ModelAttribute(WebKeys.MODEL) final DataDict dict,
                         final BindingResult bindingResult, final ModelMap model) {
        if (bindingResult.hasErrors()) {
            return VIEW_FORM;
        }

        try {
            dataDictManager.update(dict);
        } catch (DataDictExistsException e) {
            model.addAttribute(WebKeys.MESSAGES, new String[]{"I0001"});
            return VIEW_FORM;
        }

        model.clear();

        return REDIRECT_VIEW_PATH;
    }

    @RequestMapping(params = {WebKeys.ID, WebKeys.ACTION_DELETE}, method = RequestMethod.GET)
    public String delete(@RequestParam final Long id, final ModelMap model) {
        dataDictManager.delete(id);
        model.clear();

        return REDIRECT_VIEW_PATH;
    }

    @RequestMapping(value = "/imports", method = RequestMethod.POST)
    public String imports(@ModelAttribute("fileUploadForm") FileUploadForm form, final ModelMap model) throws IOException {
        if (form.getFile().isEmpty()) {
            model.addAttribute(WebKeys.MESSAGES, "I0002");
            return VIEW_HOME;
        }

        try (InputStream inputStream = form.getFile().getInputStream()) {
            dataDictManager.imports(inputStream);
        }

        return REDIRECT_VIEW_PATH;
    }

    @RequestMapping(value = "/exports", method = {RequestMethod.GET, RequestMethod.POST})
    public HttpEntity<byte[]> exports() throws IOException {
        Path path = dataDictManager.exports();

        return FssWebUtils.downloadFile(path, MediaType.APPLICATION_XML);
    }
}
