package com.ludo.study.studymatchingplatform.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.ludo.study.studymatchingplatform.study.domain.recruitment.position.Position;
import com.ludo.study.studymatchingplatform.study.domain.study.category.Category;
import com.ludo.study.studymatchingplatform.study.repository.recruitment.position.PositionRepositoryImpl;
import com.ludo.study.studymatchingplatform.study.repository.study.category.CategoryRepositoryImpl;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DbInitializer {

	private static final String CATEGORY_PROJECT = "프로젝트";
	private static final String CATEGORY_CODING_TEST = "코딩 테스트";
	private static final String CATEGORY_INTERVIEW = "모의 면접";

	private static final String POSITION_BACK = "백엔드";
	private static final String POSITION_FRONT = "프론트엔드";
	private static final String POSITION_DESIGN = "디자이너";
	private static final String POSITION_DEVOPS = "데브옵스";

	private final CategoryRepositoryImpl categoryRepository;
	private final PositionRepositoryImpl positionsRepository;

	@PostConstruct
	public void save() {
		log.info("===== POST CONSTRUCT =====");
		saveCategories(createCategories());
		savePositions(createPositions());
	}

	@PreDestroy
	public void delete() {
		log.info("===== PRE DESTROY =====");
		deleteCategories(findCategories());
		deletePositions(findPositions());
	}

	private void saveCategories(List<Category> categories) {
		for (Category category : categories) {
			categoryRepository.save(category);
		}
	}

	private List<Category> createCategories() {
		List<Category> categories = new ArrayList<>();
		categories.add(createCategory(CATEGORY_PROJECT));
		categories.add(createCategory(CATEGORY_CODING_TEST));
		categories.add(createCategory(CATEGORY_INTERVIEW));
		return categories;
	}

	private Category createCategory(String name) {
		return Category.builder()
				.name(name)
				.build();
	}

	private void savePositions(List<Position> positions) {
		for (Position position : positions) {
			positionsRepository.save(position);
		}
	}

	private List<Position> createPositions() {
		List<Position> positions = new ArrayList<>();
		positions.add(createPosition(POSITION_BACK));
		positions.add(createPosition(POSITION_FRONT));
		positions.add(createPosition(POSITION_DESIGN));
		positions.add(createPosition(POSITION_DEVOPS));
		return positions;
	}

	private Position createPosition(String name) {
		return Position.builder()
				.name(name)
				.build();
	}

	private List<Position> findPositions() {
		List<Position> positions = new ArrayList<>();
		positions.add(positionsRepository.findByName(POSITION_BACK).get());
		positions.add(positionsRepository.findByName(POSITION_FRONT).get());
		positions.add(positionsRepository.findByName(POSITION_DESIGN).get());
		positions.add(positionsRepository.findByName(POSITION_DEVOPS).get());
		return positions;
	}

	private List<Category> findCategories() {
		List<Category> categories = new ArrayList<>();
		categories.add(categoryRepository.findByName(CATEGORY_PROJECT).get());
		categories.add(categoryRepository.findByName(CATEGORY_CODING_TEST).get());
		categories.add(categoryRepository.findByName(CATEGORY_INTERVIEW).get());
		return categories;
	}

	public void deleteCategories(List<Category> categories) {
		for (Category category : categories) {
			categoryRepository.remove(category);
		}
	}

	public void deletePositions(List<Position> positions) {
		for (Position position : positions) {
			positionsRepository.remove(position);
		}
	}

}
