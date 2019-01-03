package com.shape.web.resource;

import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * Created by hootting on 2016. 9. 18..
 */
public class PageResource<T> extends ResourceSupport implements Page<T> {

    private final Page<T> page;

    public PageResource(Page<T> page, String pageParam,
                        String sizeParam) {
        super();
        this.page = page;
        if (page.hasPrevious()) {
            String path = createBuilder()
                    .queryParam(pageParam, page.getNumber() - 1)
                    .queryParam(sizeParam, page.getSize())
                    .build()
                    .toUriString();
            Link link = new Link(path, Link.REL_PREVIOUS);
            add(link);
        }
        if (page.hasNext()) {
            String path = createBuilder()
                    .queryParam(pageParam, page.getNumber() + 1)
                    .queryParam(sizeParam, page.getSize())
                    .build()
                    .toUriString();
            Link link = new Link(path, Link.REL_NEXT);
            add(link);
        }

        Link link = buildPageLink(pageParam, 0, sizeParam, page.getSize(), Link.REL_FIRST);
        add(link);

        int indexOfLastPage = page.getTotalPages() - 1;
        link = buildPageLink(pageParam, indexOfLastPage, sizeParam, page.getSize(), Link.REL_LAST);
        add(link);

        link = buildPageLink(pageParam, page.getNumber(), sizeParam, page.getSize(), Link.REL_SELF);
        add(link);
    }

    private ServletUriComponentsBuilder createBuilder() {
        return ServletUriComponentsBuilder.fromCurrentRequestUri();
    }

    private Link buildPageLink(String pageParam, int page, String sizeParam, int size, String rel) {
        String path = createBuilder()
                .queryParam(pageParam, page)
                .queryParam(sizeParam, size)
                .build()
                .toUriString();
        return new Link(path, rel);
    }

    @Override
    public int getNumber() {
        return page.getNumber();
    }

    @Override
    public int getSize() {
        return page.getSize();
    }

    @Override
    public int getTotalPages() {
        return page.getTotalPages();
    }

    @Override
    public int getNumberOfElements() {
        return page.getNumberOfElements();
    }

    @Override
    public long getTotalElements() {
        return page.getTotalElements();
    }

    @Override
    public <S> Page<S> map(Converter<? super T, ? extends S> converter) {
        return null;
    }

    @Override
    public Iterator<T> iterator() {
        return page.iterator();
    }

    @Override
    public List<T> getContent() {
        return page.getContent();
    }

    @Override
    public boolean hasContent() {
        return page.hasContent();
    }

    @Override
    public Sort getSort() {
        return page.getSort();
    }

    @Override
    public boolean isFirst() {
        return page.isFirst();
    }

    @Override
    public boolean isLast() {
        return page.isLast();
    }

    @Override
    public boolean hasNext() {
        return page.hasNext();
    }

    @Override
    public boolean hasPrevious() {
        return page.hasPrevious();
    }

    @Override
    public Pageable nextPageable() {
        return page.nextPageable();
    }

    @Override
    public Pageable previousPageable() {
        return page.previousPageable();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PageResource<?> that = (PageResource<?>) o;

        return Objects.equals(page, that.page);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), page);
    }
}
