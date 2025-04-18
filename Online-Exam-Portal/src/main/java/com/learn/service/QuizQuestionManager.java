package com.learn.service;

import java.util.List;
import java.util.Random;

import com.learn.db.dao.QuizQuestionDao;
import com.learn.db.dao.UserQuizQuestionAnswerDao;
import com.learn.db.model.QuizQuestion;
import com.learn.service.exception.CustomException;
import com.learn.service.exception.InfoMessage;

public class QuizQuestionManager {

	private final QuizQuestionDao quizQuestionDao;
	private final UserQuizQuestionAnswerDao userAnswerDao;

	public QuizQuestionManager() {
		quizQuestionDao = new QuizQuestionDao();
		userAnswerDao = new UserQuizQuestionAnswerDao();
	}

	// Fetch next unattempted question for the user from this quiz
	public QuizQuestion getNextQuestion(Long userId, Long quizId) throws InfoMessage {
		List<QuizQuestion> unattemptedQuestions = quizQuestionDao.getNotAttemptedQuestions(userId, quizId);
		System.out.println("Unattempted questions: " + unattemptedQuestions);
		if (unattemptedQuestions == null || unattemptedQuestions.isEmpty()) {
			System.out.println("All questions attempted.");
			throw new InfoMessage("All questions attempted.");

		}

		int randomIndex = new Random().nextInt(unattemptedQuestions.size());
		return unattemptedQuestions.get(randomIndex);
	}

	// Submit answer and return next unattempted question
	public QuizQuestion submitAnswerGetNextQues(Long userId, Long quizQuestionId, Long quizId, String selectedOption)
			throws CustomException, InfoMessage {

		if (!quizQuestionDao.isQuestionExists(quizId, quizQuestionId)) {
			throw new CustomException("Invalid quiz or question.");
		}

		boolean added = userAnswerDao.addQuizQuestion(userId, quizQuestionId, selectedOption);
		if (!added) {
			throw new CustomException("Could not save your answer. Please try again.");
		}

		// Return next question after submitting answer
		return getNextQuestion(userId, quizId);
	}
}
