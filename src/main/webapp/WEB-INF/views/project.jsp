<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<!--[if lt IE 7]> <html class="lt-ie9 lt-ie8 lt-ie7" lang="en"> <![endif]-->
<!--[if IE 7]> <html class="lt-ie9 lt-ie8" lang="en"> <![endif]-->
<!--[if IE 8]> <html class="lt-ie9" lang="en"> <![endif]-->
<!--[if gt IE 8]><!-->
<html lang="en"> <!--<![endif]-->
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>Teamkerbell</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Theme style -->
    <link rel="stylesheet" href="../css/wait1.css">
    <link rel="stylesheet" href="../css/jquery-jvectormap-1.2.2.css">
    <!-- daterange picker -->
    <link rel="stylesheet" href="../css/daterangepicker-bs3.css">
    <link rel="stylesheet" href="../css/bootstrap-timepicker.min.css">

    <!--tags-->
    <link href="../css/bootstrap-tokenfield.css" type="text/css" rel="stylesheet">
    <!-- end tags-->
    <!--Files-->
    <link href="../css/dataTables.bootstrap.css" type="text/css" rel="stylesheet">
    <!-- Select2 -->
    <link rel="stylesheet" href="../css/select2.min.css">
    <!-- dropzone-->
    <link rel="stylesheet" href="../css/dropzone.css">
    <link rel="stylesheet" href="../css/basic.css">


    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!--gallery-->
    <link rel="stylesheet" href="../css/ImageZoom.css"/>
    <link rel="stylesheet" href="../css/wait2.css">

</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <!-- Main Header -->
    <header class="main-header">

        <!-- Logo -->
        <a href="../dashboard/${user.id}" class="logo">
            <span class="logo-mini"><b>T</b>eam</span>
            <span class="logo-lg"><b>Teamker</b>Bell</span>
        </a>

        <!-- Header Navbar -->
        <nav class="navbar navbar-static-top" role="navigation">
            <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">Toggle navigation</span>
            </a>

            <!-- Navbar Right Menu -->
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">

                    <li data-toggle="modal" data-target="#photoModal">
                        <a data-toggle="tooltip" title="" data-placement="bottom" data-original-title="Gallery">
                            <i class="fa fa-picture-o"></i>
                        </a>
                    </li>


                    <li data-toggle="modal" data-target="#todoList">
                        <a data-toggle="tooltip" title="" data-placement="bottom" data-original-title="Todo lists">
                            <i class="fa fa-edit"></i>
                        </a>
                    </li>

                    <!--<li  onclick="location.href='../filemanager/${project.projectidx}';">
                        <a data-toggle="tooltip" title="" data-placement="bottom"data-original-title="File Manager">
                            <i class="fa fa-file-text-o"></i>
                        </a>

                    </li>

                    <li onclick="location.href='../calendar/${project.projectidx}';">
                        <a data-toggle="tooltip" title="" data-placement="bottom"data-original-title="Calendar">
                            <i class="fa fa-calendar-o"></i>
                        </a>
                    </li>-->
                    <li class="dropdown notifications-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdwon">
                            <i class="fa fa-bell-o" id="alarm"></i>
                            <span class="label label-warning" id="alarm-size">${alarm.size()}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="header" id="alarm-content">You have ${alarm.size()} notifications</li>
                            <li>
                                <ul class="menu" style="max-height:400px;overflow-y:auto" id="alarm-list">
                                    <c:forEach var="list" items="${alarm}">
                                        <li id="alarm-${list.alarmidx}">
                                            <a href="#">
                                                <i class="fa fa-users text-aqua"></i><strong>${list.actor.id}</strong>
                                                has invited you
                                                to <strong>${list.project.name}</strong>
                                                <div style="float:right;">
                                                    <button type="button" class="btn btn-primary btn-xs"
                                                            onclick="accept('${list.alarmidx}')">Ok
                                                    </button>
                                                    <button type="button" class="btn btn-default btn-xs"
                                                            onclick="decline('${list.alarmidx}')">Cancel
                                                    </button>
                                                </div>
                                            </a>
                                        </li>
                                    </c:forEach>

                                </ul>
                            </li>
                            <li class="footer"> <!--<a href="#">View all</a></li>-->
                        </ul>
                    </li>
                    <!-- User Account Menu -->
                    <li class="dropdown user user-menu">
                        <!-- Menu Toggle Button -->
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <!-- The user image in the navbar-->
                            <img src="../${user.img}" class="user-image" alt="" style="width:25px;height:25px">
                            <!-- hidden-xs hides the username on small devices so only the image appears. -->
                            <span class="hidden-xs">${user.id}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-- The user image in the menu -->
                            <li class="user-header">
                                <img src="../${user.img}" class="img-circle" alt="User Image"
                                     style="width:90px;height:90px">
                                <p>
                                    ${user.id}
                                </p>
                            </li>
                            <!-- Menu Body -->
                            <li class="user-body">
                                <div class="row">
                                    <div class="col-xs-6 text-center" style="border-right:1px solid;">
                                        <a href="../projectmanager"><i class="fa fa-pencil-square-o"></i> Project
                                            Edit</a>
                                    </div>
                                    <div class="col-xs-6 text-center">
                                        <a href="../userInfo/${user.id}"><i class="fa fa-info-circle"></i> MyInfo
                                            Edit</a>
                                    </div>

                                </div>
                                <!-- /.row -->
                            </li>
                            <!-- Menu Footer-->
                            <li class="user-footer">
                                <!--<div class="pull-left">
                                    <a href="#" class="btn btn-default btn-flat">Profile</a>
                                </div>-->
                                <div class="pull-right">
                                    <a href="../logout" class="btn btn-default btn-flat">Sign out</a>
                                </div>
                            </li>
                        </ul>
                    </li>

                </ul>
            </div>
        </nav>
    </header>
    <!-- Left side column. contains the logo and sidebar -->
    <aside class="main-sidebar">

        <!-- sidebar: style can be found in sidebar.less -->
        <section class="sidebar">

            <!-- Sidebar user panel (optional) -->
            <div class="user-panel">
                <div class="pull-left image">
                    <img src="../${user.img}" class="img-circle main-img" alt="User Image"
                         style="width:100px;height:100px">
                </div>
            </div>
            <div class="user-panel">

                <div class="info pull-left">
                    <p>${user.id}</p>
                    <!-- Status -->
                    <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
                </div>
            </div>

            <!-- Sidebar Menu -->
            <ul class="sidebar-menu">
                <li class="header"> Main Menu</li>
                <!-- Optionally, you can add icons to the links -->

                <li class="treeview">
                    <a href="#"><i class="fa fa-user"></i><span> My Profile</span></a>
                    <ul class="treeview-menu">
                        <li class="active"><a href=../userInfo/${user.id}><i class="fa fa-key"></i> Change user Info</a>
                        </li>

                    </ul>
                </li>

                <li class="treeview active">
                    <a href="#"><i class="fa fa-users"></i><span> Project rooms</span></a>
                    <ul class="treeview-menu">
                        <li>
                            <c:forEach var="list" items="${projects}">
                        <li><a href="../chat/${list.projectidx}"><i class="fa fa-folder-open-o"></i> <span
                                style="font-size:18px">${list.name} </span></a></li>
                        </c:forEach>
                        <li>
                            <a href="../projectmanager"> <i class="fa fa-cogs"></i><span
                                    style="font-size:18px">Edit</span></a></li>
                        </li>
                    </ul>
                </li>

            </ul>
            <!-- /.sidebar-menu -->
        </section>
        <!-- /.sidebar -->
    </aside>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                과제방
                <i class="fa fa-user-plus" data-toggle="modal" data-target="#InviteUser"></i>
                <small> ${project.name}</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="../dashboard"><i class="fa fa-dashboard"></i> Dashboard</a></li>
                <li class="active">Here</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-md-10 col-md-offset-1">

                    <div class="box">
                        <div class="box-header with-border">
                            <h3 class="box-title">파일 관리자</h3>
                            <div class="box-tools pull-right">
                                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                        class="fa fa-minus"></i>
                                </button>

                            </div>

                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <div class="sidebar-form">
                                <div class="input-group">
                                    <input type="text" class="form-control"
                                           id="tokenfield-typeahead"
                                           placeholder="Type something and hit enter for tags"/>
                                    <span class="input-group-btn">
                                    <button type="button" class="btn btn-flat" onclick="searchTable()">
                                    <i class="fa fa-search fa-2x"></i></button></span>
                                </div>

                            </div>


                            <table id="example2" class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Uploader</th>
                                    <th>Main Contents</th>
                                </tr>
                                </thead>
                                <tbody>

                                </tbody>
                                <tfoot>
                                <tr>
                                    <th>Name</th>
                                    <th>Uploader</th>
                                    <th>Main Contents</th>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                    </div>
                    <!-- /.box-body -->
                </div>
            </div>


            <!-- /.content-wrapper -->

            <div class="row">
                <div class="col-md-8 col-md-offset-1">
                    <div class="box box-primary direct-chat direct-chat-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">회의 공간</h3>

                            <div class="box-tools pull-right">
                                <button type="button" class="btn btn-box-tool" data-widget="chat-pane-toggle">
                                    <i class="fa fa-list-ul"></i>
                                </button>
                                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                        class="fa fa-minus"></i>
                                </button>

                            </div>
                        </div>
                        <form action="../file" class="dropzone" id="dropzone" method="POST"
                              enctype="multipart/form-data">
                            <!-- /.box-header -->
                            <div class="box-body">
                                <!-- Conversations are loaded here -->
                                <div class="direct-chat-messages chatbox" id="chat">

                                </div>
                                <div class="direct-chat-contacts">

                                    <!-- /.box-header -->
                                    <ul class="contacts-list">
                                        <c:forEach var="list" items="${users}">
                                            <li id="userlist-${list.id}" class="item" style="margin-right:5px">
                                                <img class="contacts-list-img" src="../${list.img}" alt="User Image"
                                                     style="margin-left:2px; width:40px;height:40px">
                                                <div class="contacts-list-info">

                                                    <span class="contacts-list-name">
                                                        ${list.id}
                                                       <a href="#" style="margin-left:3px">
                                                    <i id="user${list.id}on"
                                                       class="fa fa-circle st-cir text-success hidden"></i>
                                                    <i id="user${list.id}off"
                                                       class="fa fa-circle st-cir text-warning"></i>
                                                            </a>
                                                    </span>


                                                    <span class="contacts-list-msg pull-left" id="userstatus">
                                                        His or Her name is ${list.name}
                                                    </span>
                                                </div>
                                            </li>
                                        </c:forEach>

                                        <!-- /.item -->
                                    </ul>
                                </div>

                            </div>
                            <input type="hidden" name="idx" value="${project.projectidx}"/>
                            <input type="hidden" name="userIdx" value="${user.useridx}"/>

                        </form>


                        <!-- /.box-body -->
                        <div class="box-footer">
                            <div class="input-group">
                                <input type="text" id="typing" name="message" placeholder="Type Message ..."
                                       class="form-control" onkeypress="if(event.keyCode==13)sendMsg()">
                                <span class="input-group-btn">
                            <button type="button" type="button" class="btn btn-primary btn-flat" onClick="sendMsg()">
                                Send
                            </button>
                            <span onclick="fileDialog()">
                            <button type="button" class="btn btn-default btn-sm" data-toggle="tooltip" title=""
                                    data-original-title="File Upload" style="margin-left:3px;height:34px">
                 			 <i class="fa fa-paperclip fa-2x"></i></button>
                 			 </span>
                 			  <span data-toggle="modal" data-target="#todoMadal">
                 			  <button type="button" class="btn btn-default btn-sm" data-toggle="tooltip" title=""
                                      data-original-title="Add TodoList" style="margin-left:3px;height:34px">
                 			 <i class="fa fa-pencil-square-o fa-2x"></i></button>

                 			 </span>
                             <span data-toggle="modal" data-target="#scheduleModal">
                              <button type="button" class="btn btn-default btn-sm" data-toggle="tooltip" title=""
                                      data-original-title="Add Meeting" style="margin-left:3px;height:34px">
                 			 <i class="fa fa-calendar-plus-o fa-2x"></i></button>
                                </span>
                            </span>
                            </div>
                        </div>
                    </div> <!-- box -->
                    <!-- /.box-footer-->
                </div>    <!-- col -->


                <!-- memo -->

                <div class="col-md-2">
                    <div class="box box-primary">
                        <div class="box-header with border">
                            <h3 class="box-title">회의록</h3>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <div class="form-group">
                                <label>Date</label>
                                <select class="form-control select2" style="width: 100%;" id="selectBox">
                                    <option selected="selected">Today</option>
                                    <c:forEach var="list" items="${minutes}">
                                        <option value="${list.content}">${list.date}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="form-group">
                                <textarea id="memo" class="form-control" rows="13"
                                          disabled>${project.minute}</textarea>
                            </div>
                            Written by <kbd id="writer">?</kbd>
                        </div>
                        <!-- /.box-body -->
                        <div class="box-footer">
                            <div class="pull-right">
                                <button id="writebutton" type="button" class="btn btn-default" onClick="writeMemo()"><i
                                        class="fa fa-pencil"></i> 쓰기
                                </button>
                                <button id="savebutton" type="button" class="btn btn-primary hidden"
                                        onClick="saveMemo()"><i class="fa fa-floppy-o"></i> 저장
                                </button>
                            </div>
                        </div>
                        <!-- /.box-footer -->
                    </div>

                    <!-- /. box -->
                </div>


            </div>
        </section>
    </div>
    <!-- Main Footer -->
    <footer class="main-footer">
        <!-- To the right -->
        <div class="pull-right hidden-xs">
            Teamkerbell
        </div>
        <!-- Default to the left -->
        <!-- <strong> &copy; 2015 <a href="#">Company</a>.</strong> All rights reserved.-->
    </footer>


    <div class="control-sidebar-bg"></div>


    <!-- /.content -->

    <!-- ./wrapper -->

    <div class="modal fade" id="uploadModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content file_content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="exampleModalLabel">File Upload</h4>
                </div>
                <form id="uploadForm" method="POST" enctype="multipart/form-data">
                    <div class="modal-body file_body">
                        <div class="form-group">
                            <div id="up_field">
                                <input type="hidden" id="idx" name="idx" value="${project.projectidx}"/>
                                <input type="hidden" name="userIdx" value="${user.useridx}"/>
                                <div class="form-group">


                                    <input type="text" id="fakeFileTxt" class="fakeFileTxt" readonly="readonly"
                                           multiple>
                                    <div class="fileDiv">
                                        <input type="button" value="Select File" onclick="fileDialog()"
                                               class="buttonImg"/>
                                        <input type="file" id="file" class="realFile" onChange="upload()"
                                               name="File[]"/>
                                    </div>


                                </div>

                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" onclick="upload()">Upload</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- REQUIRED JS SCRIPTS -->


    <div class="modal fade" id="todoList" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="exampleModalLabel">Project To Do List</h4>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <ul class="todo-list pro-todo">
                            <c:forEach var="list" items="${todolist}">
                                <c:choose>
                                    <c:when test="${list.ok=='0'}">
                                        <li class="done">
                                    </c:when>
                                    <c:otherwise>
                                        <li>
                                    </c:otherwise>
                                </c:choose>
                                <img src="../${list.user.img}" class="img-circle img-bordered-sm" alt="user image">
                                <span class="username">
                          <span>${list.user.id}</span>
                        </span>
                                <span class="text">${list.content}</span>
                                <!-- Emphasis label -->
                                <small class="label label-danger" prettydate><i
                                        class="fa fa-clock-o"></i>${list.enddate}
                                </small>
                                <!-- General tools such as edit or delete-->
                                </li>
                            </c:forEach>
                            <!-- todolist end-->
                        </ul>


                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>

    <div class="modal fade" id="photoModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="exampleModalLabel">Image Gallery</h4>
                </div>
                <form>
                    <div class="modal-body">

                        <div class="box-body">

                            <div class="gallery">

                                <c:forEach var="list" items="${img}">
                                    <a href="../loadImg?name=${list.storedname}" class="zoom">
                                        <img src="../loadImg?name=${list.storedname}" width="170" height="120"
                                             alt="An elegant profile" style="margin-top:3%;margin-right:1%">
                                    </a>
                                </c:forEach>

                            </div>
                        </div>


                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </form>
            </div>
        </div>
    </div>


    <div class="modal fade" id="todoMadal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="exampleModalLabel">Enroll TodoList</h4>
                </div>
                <form id="todoform">
                    <div class="modal-body">
                        <div id="success-message" class="alert alert-info collapse" role="alert">
                            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                            <span class="sr-only">Info:</span>
                            Enroll Success
                        </div>

                        <div id="error-message" class="alert alert-danger collapse" role="alert">
                            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                            <span class="sr-only">Error:</span>
                            Failed!
                        </div>
                        <div class="form-group">
                            <label>Date range:</label>

                            <div class="input-group">
                                <div class="input-group-addon">
                                    <i class="fa fa-calendar"></i>
                                </div>
                                <input type="text" class="form-control pull-right" id="reservation">
                            </div>
                            <br>
                            <label> User name</label>
                            <select class="form-control select2" data-placeholder="Select a State"
                                    style="width: 100%;" id="todoselect"> <!-- multiple = "multiple" -->
                                <c:forEach var="list" items="${users}">
                                    <option value="${list.id}">${list.name}</option>
                                </c:forEach>
                            </select>
                            <br>
                            <br>
                            <div class="form-group has-success">
                                <label class="control-label" for="inputSuccess"><i class="fa fa-check"></i> List what
                                    you
                                    have to do</label>
                                <input type="text" id="todocontent" class="form-control" id="inputSuccess"
                                       placeholder="To do ...">
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" onclick="makeTodolist()">Submit</button>
                    </div>
                </form>
            </div>
        </div>
    </div>


    <div class="modal fade" id="scheduleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="exampleModalLabel">Enroll Schedule</h4>
                </div>
                <form id="scheduleform">
                    <div class="modal-body">
                        <div id="success-message" class="alert alert-info collapse" role="alert">
                            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                            <span class="sr-only">Info:</span>
                            Enroll Success
                        </div>

                        <div id="error-message" class="alert alert-danger collapse" role="alert">
                            <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                            <span class="sr-only">Error:</span>
                            Failed!
                        </div>
                        <div class="form-group">
                            <label>Date range:</label>

                            <div class="input-group">
                                <div class="input-group-addon">
                                    <i class="fa fa-calendar"></i>
                                </div>
                                <input type="text" class="form-control pull-right" id="scheduleReservation">
                            </div>
                            <div class="form-group has-success">
                                <label class="control-label" for="inputSuccess"><i class="fa fa-check"></i>To do</label>
                                <input type="text" id="goal" class="form-control"
                                       placeholder="Your Goal">
                            </div>

                            <div class="form-group has-success">
                                <label class="control-label" for="inputSuccess"><i class="fa fa-check"></i>Place</label>
                                <input type="text" id="place" class="form-control"
                                       placeholder="Place">
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary" onclick="makeSchedule()">Submit</button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <div class="modal fade" id="downloadModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="exampleModalLabel">File lists</h4>
                </div>

                <div class="modal-body">

                    <div class="table-responsive">
                        <table class="table no-margin">
                            <thead>
                            <tr>
                                <th>Index</th>
                                <th>File</th>
                                <th>Uploader</th>
                                <th>Date</th>
                            </tr>
                            </thead>
                            <tbody id="filetable">

                            </tbody>
                        </table>
                    </div>
                </div>
                <div class="modal-footer">
                    <ul class="pager">
                        <li>
                            <a class="previous" href="#" onclick="openFileFlag(1)">Previous</a>
                        </li>
                        <li>
                            <a href="#" class="alert-success" data-dismiss="modal">Close</a>
                        </li>
                        <li>
                            <a class="next" href="#" onclick="openFileFlag(2)">Next</a>
                        </li>
                    </ul>


                </div>
            </div>
        </div>
    </div>


    <div class="modal fade" id="InviteUser" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">&times;</span></button>
                    <h4 class="modal-title" id="exampleModalLabel">Invite User</h4>
                </div>
                <div id="error-message" class="alert alert-danger collapse" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <span class="sr-only">Error:</span>
                    Wrong Person!
                </div>
                <div id="success-message" class="alert alert-info collapse" role="alert">
                    <span class="glyphicon glyphicon-exclamation-sign" aria-hidden="true"></span>
                    <span class="sr-only">Info:</span>
                    Invite Success
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <!-- class="sidebar-form" -->
                        <div id="inviteForm" class="sidebar-form">
                            <div class="input-group">
                                <input type="text" id="inviteId" class="form-control" placeholder=" User ID Search...">
                                <span class="input-group-btn">
                	<button type="button" class="btn btn-flat" onClick="search()"><i
                            class="fa fa-search"></i>
                    </button>
              		</span>
                            </div>

                            <!-- Profile Image -->
                            <!-- at fist, not in here, after searching there will be -->
                            <div id="user"></div>

                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- jQuery 2.1.4 -->
<script src="../js/jQuery-2.1.4.min.js"></script>
<script src="../js/jquery-ui.min.js"></script>
<!-- Bootstrap 3.3.5 -->
<script src="../js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="../js/app.min.js"></script>
<script src="../js/date.js"></script>
<!-- date-range-picker -->
<script src="../js/moment.min.js"></script>
<script src="../js/bootstrap-timepicker.min.js"></script>
<script src="../js/daterangepicker.js"></script>
<!-- Select -->
<script src="../js/select2.full.min.js"></script>
<!-- json2 -->
<script src="../js/json2.js"></script>
<!-- alarm -->
<script src="../js/alarm.js"></script>
<!-- SocketIO -->
<script src="../js/socket.io.js"></script>
<!-- prettydate -->
<script src="../js/prettydate.min.js"></script>
<!-- filepicker -->
<script src="../js/jquery.filepicker.js"></script>
<!-- dropzone -->
<script src="../js/dropzone.js"></script>


<!--Table-->
<script src="../js/jquery.dataTables.min.js"></script>
<script src="../js/dataTables.bootstrap.min.js"></script>

<!-- tag -->
<script type="text/javascript" src="../js/bootstrap-tokenfield.js" charset="UTF-8"></script>
<script type="text/javascript" src="../js/typeahead.bundle.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="../js/docs.min.js" charset="UTF-8"></script>

<!-- gallery-->
<script type="text/javascript" src="../js/ImageZoom.js"></script>

<script type="text/javascript" src="../js/project/default.js"></script>
<script type="text/javascript" src="../js/project/functionP.js"></script>
<script type="text/javascript" src="../js/project/socket.js"></script>

<script>
    var projectIdx =${project.projectidx};
    var user = {
        useridx:${user.useridx},
        id: '${user.id}',
        name: '${user.name}',
        img: '${user.img}'
    }
    var option = 'Today';
    var currentMinute = '${project.minute}';
    var socket,table;
    init();
    socketInit(user, projectIdx);
</script>
</body>
</html>