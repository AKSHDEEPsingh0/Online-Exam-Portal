<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <title>Admin Login</title>

    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500&display=swap" rel="stylesheet">

    <!-- Custom Styles -->
    <style>
        body {
            font-family: 'Poppins', sans-serif;
            background: url('img/bg.jpg') center center fixed;
            background-size: cover;
            background-repeat: no-repeat;
            background-color: #f0f0f0;
            color: #333;
        }

        .box-area {
            width: 930px;
        }

        .right-box {
            padding: 40px 30px;
        }

        .rounded-4 {
            border-radius: 20px;
        }

        .rounded-5 {
            border-radius: 30px;
        }

        @keyframes slideRight {
            from {
                opacity: 0;
                transform: translateX(-20px);
            }
            to {
                opacity: 1;
                transform: translateX(0);
            }
        }

        .sliding-paragraph {
            animation: slideRight 2s ease-in-out;
        }

        @media only screen and (max-width: 768px) {
            .box-area {
                margin: 0 10px;
            }

            .left-box {
                height: 100px;
                overflow: hidden;
            }

            .right-box {
                padding: 20px;
            }
        }
    </style>
</head>

<body>
<!-- Main Container -->
<div class="container d-flex justify-content-center align-items-center min-vh-100">
    <!-- Login Container -->
    <div class="row border rounded-5 p-3 bg-white shadow box-area">
        <!-- Left Box -->
        <div class="col-md-6 rounded-4 d-flex justify-content-center align-items-center flex-column left-box"
             style="background: #103cbe;">
            <div class="featured-image mb-3">
                <img src="img/1.jpg" class="img-fluid" style="width: 200px;">
            </div>
            <p class="text-white fs-2" style="font-family: 'Courier New', Courier, monospace; font-weight: 600;">Be
                Verified</p>
            <small class="text-white text-center"
                   style="width: 17rem;font-family: 'Courier New', Courier, monospace;">Join experienced Designers on
                this platform.</small>
        </div>

        <!-- Right Box -->
        <div class="col-md-6 right-box">
            <div class="row align-items-center">
                <div class="header-text mb-4">
                    <h2 class="mb-3" style="font-family: 'Algerian', sans-serif; font-size: 2.5rem; text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.3);">HELLO, ADMIN</h2>
                    <p class="sliding-paragraph" style="font-family: 'Arial', sans-serif; font-size: 1.2rem;">We are happy to have you back!</p>
                </div>

                <!-- Display error message if present -->
                <%
                    String error = (String) session.getAttribute("error");
                    if (error != null) {
                %>
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                    <%= error %>
                    <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                </div>
                <% session.removeAttribute("error"); %>
                <%
                    }
                %>

                <!-- Login Form -->
                <form class="user" action="${pageContext.request.contextPath}/admin/login" method="post">
                    <div class="input-group mb-3">
                        <input name="username" type="text" class="form-control form-control-lg rounded-pill bg-light text-dark fs-6"
                               placeholder="Email Address" required>
                    </div>
                    <div class="input-group mb-4">
                        <input name="password" type="password" class="form-control form-control-lg rounded-pill bg-light text-dark fs-6"
                               placeholder="Password" required>
                    </div>
                    <div class="input-group mb-3">
                        <button class="btn btn-lg btn-primary w-100 fs-6">Login</button>
                    </div>
                </form>
            </div>
        </div>

    </div>
</div>

<!-- Bootstrap JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>

</html>
