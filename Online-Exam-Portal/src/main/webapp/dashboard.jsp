<%@ page import="com.learn.db.model.Quiz" %>
<%@ page import="java.util.List" %>

<jsp:include page="includes/header_dashboard.jsp" />
<style>
  .page-heading.contact-heading.header-text {
    background-color: rgba(0, 0, 0, 0.5); /* dark overlay */

    /*padding: 120px 0;*/
    color: white;
    text-align: center;
    position: relative;
    z-index: 1;
  }

  .page-heading.contact-heading.header-text::before {
    content: "";
    background: url('${pageContext.request.contextPath}/assets/images/heading-image.jpeg') no-repeat center center;
    background-size: cover;
    position: absolute;
    top: 0; left: 0;
    width: 100%;
    height: 100%;
    z-index: -1;
  }

  .text-content h4 {
    font-size: 40px;
    font-weight: 600;
    color: white;
  }
</style>

<div class="page-heading contact-heading header-text position-relative"
     style="background-image: url('<%= request.getContextPath() %>/assets/images/heading-image.jpeg');
             background-size: cover;
             background-position: center;
             height: 300px;
             display: flex;
             align-items: center;
             justify-content: center;">

  <!-- Dark overlay -->
  <div class="overlay position-absolute w-100 h-100"
       style="background-color: rgba(0, 0, 0, 0.6); top: 0; left: 0; z-index: 1;"></div>

  <!-- Heading Text -->
  <div class="container text-center text-white position-relative" style="z-index: 2;">
    <h4 class="display-5 fw-bold">Explore Quiz</h4>
  </div>
</div>

<section class="vh-100 gradient-custom-2 bg-primary-subtle">
  <div class="container py-5 h-100">
    <div class="row d-flex justify-content-top align-items-top h-100">
      <div class="col-xl-12 col-xl-10">
        <div class="card mask-custom bg-primary">
          <div class="card-body p-4 text-white">
            <div class="text-center pt-3 pb-2">
              <h2 class="my-4">Quiz List</h2>
            </div>

            <%
              List<Quiz> quizList = (List<Quiz>) request.getAttribute("quizList");
            %>

            <% if (quizList != null && !quizList.isEmpty()) { %>
            <table class="table text-white mb-0 table-custom">
              <thead>
              <tr>
                <th scope="col">Serial No.</th>
                <th scope="col">Quiz Title</th>
                <th scope="col">Category</th>
                <th scope="col">Actions</th>
              </tr>
              </thead>
              <tbody>
              <% int serial = 1;
                for (Quiz quiz : quizList) { %>
              <tr>
                <td><%= serial++ %></td>
                <td title="<%= quiz.getTitle() %>"><%= quiz.getTitle() %></td>
                <td title="<%= quiz.getCategory() %>"><%= quiz.getCategory() %></td>
                <td>
                  <a href="<%= request.getContextPath() %>/start/quiz/<%= quiz.getId() %>" class="btn btn-success">Start</a>
                </td>
              </tr>
              <% } %>
              </tbody>
            </table>
            <% } else { %>
            <p class="text-white">No quizzes available at the moment.</p>
            <% } %>

          </div>
        </div>
      </div>
    </div>
  </div>
</section>

<jsp:include page="includes/footer.jsp" />