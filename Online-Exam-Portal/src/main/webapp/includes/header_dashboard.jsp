<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <link href="https://fonts.googleapis.com/css?family=Poppins:100,200,300,400,500,600,700,800,900&display=swap" rel="stylesheet">

    <title>Online Quiz - Dashboard</title>

    <!-- Bootstrap core CSS -->
    <link href="${pageContext.request.contextPath}/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Additional CSS Files -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/fontawesome.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/templatemo-sixteen.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/owl.css">
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.0.13/css/all.css" integrity="sha384-DNOHZ68U8hZfKXOrtjWvjxusGo9WQnrNx2sqG0tfsghAvtVlRW3tvkXWZh58N9jp" crossorigin="anonymous"/>

    <style>
        body {
            background-color: #c9d6ff;
            background: linear-gradient(to right, #e2e2e2, #c9d6ff);
        }
        .welcome-text {
            color: white;
            font-weight: bold;
            margin-right: 20px;
        }
    </style>
</head>

<body>

<!-- Dashboard Header -->
<header>
    <div class="d-flex flex-column flex-md-row align-items-center p-1 mb-6 px-md-4 navbar navbar-expand-md navbar-light bg-success sticky-top">
        <h2 class="my-0 mr-md-auto font-weight-normal">
            <a class="navbar-brand text-warning" href="${pageContext.request.contextPath}/dashboard.jsp">
                <h2>Online <em>Quiz</em></h2>
            </a>
        </h2>

        <!-- Welcome user text -->
        <div class="welcome-text d-none d-md-block">
            Welcome, <c:out value="${sessionScope.username}" />
        </div>

        <nav class="my-2 my-md-0 mr-md-3 navbar navbar-expand-lg">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarDashboard" aria-controls="navbarDashboard" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarDashboard">
                <ul class="navbar-nav ml-auto">
                    <li class="nav-item">
                        <a class="nav-link text-white" href="${pageContext.request.contextPath}/dashboard.jsp">Dashboard</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="${pageContext.request.contextPath}/viewProfile">Profile</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link text-white" href="${pageContext.request.contextPath}/leaderboard">Leaderboard</a>
                    </li>
                    <li class="nav-item">
                        <a class="btn btn-warning text-dark ml-2" href="${pageContext.request.contextPath}/logout">Logout</a>
                    </li>
                </ul>
            </div>
        </nav>
    </div>
</header>
