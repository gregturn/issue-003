/*
 * Copyright 2015 the original author or authors.
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
package demo;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.EntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Greg Turnquist
 */
@RepositoryRestController
public class ApplicationController {

	private final DocumentRepository repository;
	private final EntityLinks entityLinks;

	@Autowired
	public ApplicationController(DocumentRepository repository, EntityLinks entityLinks) {

		this.repository = repository;
		this.entityLinks = entityLinks;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/documents/search/findFoo/{info}")
	public ResponseEntity<?> findFoo(@PathVariable String info) {

		Resource<Document> doc = new Resource<Document>(new Document());
		doc.getContent().setInfo(info);
		doc.add(linkTo(methodOn(ApplicationController.class).findFoo(info)).withSelfRel());
		doc.add(entityLinks.linkToCollectionResource(Document.class).withRel("documents"));
		return ResponseEntity.ok(doc);
	}

}
