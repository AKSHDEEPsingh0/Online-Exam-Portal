<%@page import="java.util.List"%>
<%@page import="com.learn.db.model.Quiz"%>
<%@page import="com.learn.db.model.UserScore"%> <!-- Assuming this is your leaderboard object -->
<%@page import="com.learn.db.model.User"%>

<jsp:include page="includes/header.jsp" />

<style>
  .custom-table tbody tr:nth-child(odd) {
    background-color: #edf5fa;
  }

  .custom-table tbody tr:nth-child(even) {
    background-color: #d6eaf8;
  }
</style>

<div class="page-heading products-heading header-text">
  <div class="container">
    <div class="row">
      <div class="col-md-12">
        <div class="text-content">
          <h4>
            Quiz Name:
            <%= ((Quiz) request.getAttribute("quiz")).getTitle() %>
          </h4>
          <h2>
            Category:
            <%= ((Quiz) request.getAttribute("quiz")).getCategory() %>
          </h2>
        </div>
      </div>
    </div>
  </div>
</div>

<div class="container">
  <div class="row">
    <div class="col-md-12">
      <div class="table-responsive">
        <table class="table table-striped table-bordered mt-5 mb-2 custom-table">
          <thead class="thead-dark">
          <tr>
            <th scope="col">Sr No.</th>
            <th scope="col">User</th>
            <th scope="col">Score</th>
          </tr>
          </thead>
          <tbody>
          <%
            List<com.learn.db.model.UserScore> userScores = (List<com.learn.db.model.UserScore>) request.getAttribute("userScores");
            int count = 1;
            if (userScores != null) {
              for (com.learn.db.model.UserScore userScore : userScores) {
          %>
          <tr>
            <th scope="row"><%= count++ %></th>
            <td><%= userScore.getUserName() %></td>
            <td><%= userScore.getScore() %></td>
          </tr>
          <%
            }
          } else {
          %>
          <tr>
            <td colspan="3">No scores to display.</td>
          </tr>
          <%
            }
          %>

          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>

<jsp:include page="includes/footer.jsp" />
