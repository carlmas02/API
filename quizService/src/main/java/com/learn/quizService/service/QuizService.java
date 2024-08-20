package com.learn.quizService.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.learn.quizService.exception.InvalidQuizDataException;
import com.learn.quizService.model.Answers;
import com.learn.quizService.model.Questions;
import com.learn.quizService.model.Quiz;
import com.learn.quizService.model.Result;
import com.learn.quizService.repository.AnswerRepository;
import com.learn.quizService.repository.QuestionRepository;
import com.learn.quizService.repository.QuizRepository;
import com.learn.quizService.repository.ResultRepository;

@Service
public class QuizService {

	
	@Autowired
	private QuizRepository quizRepository;
	
	@Autowired
	private AnswerRepository answerRepository;
	
	@Autowired
	private QuestionRepository questionRepository;
	
	@Autowired
	private ResultRepository resultRepository;
	
	// Get all quizzes
	public List<Quiz> getAllQuizzes() {
		return quizRepository.findAll();
	}
	
	// Get quiz by id
	public Quiz getQuizById(String quizId) {
        return quizRepository.findById(quizId).orElseThrow(() -> new InvalidQuizDataException("Quiz with given id not found."));
    }
	
	// Get quiz by course id
	public List<Quiz> getQuizzesByCourseId(String courseId) {
        List<Quiz> quizzes = quizRepository.findByCourseId(courseId);
        if(quizzes.isEmpty())
        {
        	throw new InvalidQuizDataException("No quizzes registered for this course");
        }
        return quizzes;
    }
	
	// Get quiz by quiz title
	public List<Quiz> getQuizByQuizTitle(String quizTitle) {
        List<Quiz> quizzes = quizRepository.findByQuizTitle(quizTitle);
        if(quizzes.isEmpty())
        {
        	throw new InvalidQuizDataException("There is no quiz with the given name.");
        }
        return quizzes;
    }
	
	// Create a new quiz
	public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }
	
	// Delete a quiz
	public boolean deleteQuiz(String quizId) {
		boolean isDeleted = false;
		if(quizRepository.existsById(quizId))
		{
			// Delete all questions and associated answers
            List<Questions> questions = questionRepository.findByQuizId(quizId);
            for (Questions question : questions) {
                List<Answers> answers = answerRepository.findByQuestionId(question.getQuestionId());
                if (!answers.isEmpty()) {
                    answerRepository.deleteAll(answers);
                }
                questionRepository.delete(question);
            }
            // Delete the quiz
            quizRepository.deleteById(quizId);
            isDeleted = true;
		}
		else {
			throw new InvalidQuizDataException("Quiz with given id not found");
		}
		
		return isDeleted;
	}
	
	
	// Update a quiz
	public boolean updateQuiz(Quiz quiz) {
		boolean isUpdated = false;
        Optional<Quiz> optional = quizRepository.findById(quiz.getQuizId());
        if(optional.isPresent())
        {
            Quiz existingQuiz = optional.get();
            existingQuiz.setQuizTitle(quiz.getQuizTitle());
            existingQuiz.setCourseId(quiz.getCourseId());
            existingQuiz.setDuration(quiz.getDuration());
            existingQuiz.setCreatedAt(LocalDateTime.now());
            existingQuiz.setTotalQuestions(quiz.getTotalQuestions());
            quizRepository.save(existingQuiz);
            isUpdated = true;
        }
        else {
        	throw new InvalidQuizDataException("Quiz with given id not found");
        }
        return isUpdated;
    }
	
	// Delete all quizzes associated with a course id
    public void deleteQuizzesByCourseId(String courseId) {
        List<Quiz> quizzes = quizRepository.findByCourseId(courseId);
        if (!quizzes.isEmpty()) {
            quizRepository.deleteAll(quizzes);
        }
        else {
        	throw new InvalidQuizDataException("No quizzes registered for this course");
        }
    }
	
    // Attempt a new quiz
    public Result attemptQuiz(Map<String, String> answers, String quizId, String email) {
        // Logic to validate answers and calculate result
        Result result = new Result();
        int score = 0;
        for (Map.Entry<String, String> entry : answers.entrySet()) {
            String questionId = entry.getKey();
            String userAnswer = entry.getValue();
            Optional<Answers> correctAnswerOpt = answerRepository.findByQuestionId(questionId).stream()
                    .filter(Answers::getIsCorrect)
                    .findFirst();
            if (correctAnswerOpt.isPresent()) {
                Answers correctAnswer = correctAnswerOpt.get();
                if (correctAnswer.getAnswerText().equalsIgnoreCase(userAnswer)) {
                    // Safely handle the Optional returned by findById
                    Optional<Questions> questionOpt = questionRepository.findById(questionId);
                    if (questionOpt.isPresent()) {
                        score += questionOpt.get().getPoints();
                    } else {
                        throw new InvalidQuizDataException("Question not found");
                    }
                }
            } else {
            	throw new InvalidQuizDataException("No correct answer found for this question");
            }
        }
        result.setMarksScored(score);
        int totalScore = 0;
        List<Questions> questions = questionRepository.findByQuizId(quizId);
        for(Questions question : questions)
        {
        	totalScore += question.getPoints();
        }
        result.setTotalMarks(totalScore);
        result.setQuizId(quizId);
        result.setStudentId(email);
        result.setCourseId(quizRepository.findById(quizId).get().getCourseId());
        result.setAttemptedOn(LocalDateTime.now());
        resultRepository.save(result);
        return result;
    }
	
}
