package com.learn.db.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.learn.database.DatabaseConnection;
import com.learn.db.model.Quiz;

public class QuizDao {

	public Long addQuiz(String title, String category) {
		try (Connection con = DatabaseConnection.getConnection();
				PreparedStatement statement = con.prepareStatement("insert into quiz (title, category) value (?, ?)",
						PreparedStatement.RETURN_GENERATED_KEYS)) {
			statement.setString(1, title);
			statement.setString(2, category);
			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				return rs.getLong(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean updateQuiz(Long quizId, String title, String category) {
		try (Connection con = DatabaseConnection.getConnection();
				PreparedStatement statement = con
						.prepareStatement("update quiz set title = ?, category = ?  where id = ? and deleted = 0")) {
			statement.setString(1, title);
			statement.setString(2, category);
			statement.setLong(3, quizId);
			int updateCount = statement.executeUpdate();
			return updateCount == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public boolean deleteQuiz(Long quizId) {
		try (Connection con = DatabaseConnection.getConnection();
				PreparedStatement statement = con
						.prepareStatement("update quiz set deleted = 'Y'  where id = ? and deleted = 0")) {
			statement.setLong(1, quizId);
			int updateCount = statement.executeUpdate();
			return updateCount == 1;
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}

	public Quiz getQuiz(Long quizId) {
		if(quizId!=0)
		try (PreparedStatement statement = DatabaseConnection.getConnection()
				.prepareStatement("select id, title, category from quiz where id = ? and deleted = 0 ")) {
			statement.setLong(1, quizId);
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return new Quiz(rs.getLong("id"), rs.getString("title"), rs.getString("category"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		 
		return null;
	}

	public List<Quiz> getAllQuiz() {
		List<Quiz> quizzes = new ArrayList<>();
		String sql = "SELECT id, title, category FROM quiz WHERE deleted = 0";

		try (Connection con = DatabaseConnection.getConnection();
			 PreparedStatement statement = con.prepareStatement(sql)) {

			System.out.println("Executing SQL: " + sql);
			System.out.println("Checking database connection: " + con);

			if (con == null) {
				System.out.println("Database connection is null.");
				return quizzes;
			}

			ResultSet rs = statement.executeQuery();
			System.out.println("SQL execution completed. Checking result set...");

			if (rs == null) {
				System.out.println("Result set is null.");
				return quizzes;
			}

			int count = 0;
			while (rs.next()) {
				long id = rs.getLong("id");
				String title = rs.getString("title");
				String category = rs.getString("category");
				Quiz quiz = new Quiz(id, title, category);
				quizzes.add(quiz);
				System.out.println("Fetched Quiz: ID=" + id + ", Title=" + title + ", Category=" + category);
				count++;
			}

			System.out.println("Total quizzes fetched: " + count);
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Error while fetching quizzes: " + e.getClass().getName() + " - " + e.getMessage());
			e.printStackTrace();
		}
		return quizzes;
	}




	public Long totalCount() {
		try (PreparedStatement statement = DatabaseConnection.getConnection()
				.prepareStatement("select count(id) countQuiz from quiz where deleted = 0")) {
			ResultSet rs = statement.executeQuery();
			if (rs.next()) {
				return rs.getLong("countQuiz");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return 0l;
	}

}
