package com.mangareader.manga.translator;

import com.mangareader.manga.translator.exception.TranslatorAlreadyExistsException;
import com.mangareader.manga.translator.exception.TranslatorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TranslatorService {

    private final TranslatorRepository translatorRepository;

    public Mono<Translator> create(Translator translator) {
        return translatorRepository.findById(translator.getId())
                .flatMap(unused -> Mono.<Translator>error(new TranslatorAlreadyExistsException(translator.getId())))
                .switchIfEmpty(translatorRepository.save(translator));
    }

    public Mono<Translator> update(Translator translator) {
        return translatorRepository.findById(translator.getId())
                .switchIfEmpty(Mono.error(new TranslatorNotFoundException(translator.getId())))
                .flatMap(oldTranslator -> translatorRepository.save(translator));
    }

    public Mono<Translator> findById(String id) {
        return translatorRepository.findById(id)
                .switchIfEmpty(Mono.error(new TranslatorNotFoundException(id)));
    }

    public Mono<Translator> findTranslatorByName(String name) {
        return translatorRepository.findByScan(name)
                .switchIfEmpty(Mono.error(new TranslatorNotFoundException(name)));
    }

    public Flux<Translator> findAll(Pageable pageable) {
        return translatorRepository.findAllBy(pageable);
    }

    public Mono<Translator> delete(String id) {
        return translatorRepository.findById(id)
                .switchIfEmpty(Mono.error(new TranslatorNotFoundException(id)))
                .flatMap(translator -> translatorRepository.delete(translator).thenReturn(translator));
    }

}
