<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
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


    <link href="//maxcdn.bootstrapcdn.com/font-awesome/4.1.0/css/font-awesome.min.css" rel="stylesheet"/>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"
          integrity="sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ=="
          crossorigin="anonymous">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
    <!-- Theme style -->
     <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="../css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/wait1.css">
    <link rel="stylesheet" href="../css/jquery-jvectormap-1.2.2.css">

    <link rel="stylesheet" href="../css/wait2.css">

</head>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <!-- Main Header -->
    <header class="main-header">

        <!-- Logo -->
        <a href="../dashboard/${user.id}" class="logo">
            <!-- mini logo for sidebar mini 50x50 pixels -->
            <span class="logo-mini"><b>T</b>eam</span>
            <!-- logo for regular state and mobile devices -->
            <span class="logo-lg"><b>Teamker</b>Bell</span>
        </a>

        <!-- Header Navbar -->
        <nav class="navbar navbar-static-top" role="navigation">
            <!-- Sidebar toggle button-->
            <a href="#" class="sidebar-toggle" data-toggle="offcanvas" role="button">
                <span class="sr-only">Toggle navigation</span>
            </a>
            <!-- Navbar Right Menu -->
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
                   <li class="dash-icon">
                <a class="dash-nav">
              	<i class="fa fa-picture-o"></i></a>
            	</li>
            	
           	
            	<li class="dash-icon">
            	<a class="dash-nav">
              	<i class="fa fa-edit"></i>
              	</a>
            	</li>

            	 <li class="dash-icon">
            	 <a class="dash-nav">
              	<i class="fa fa-file-text-o"></i> 
              	 </a>          	
           	   </li>
           	   <li class="dash-icon">
           	   <a class="dash-nav">
              	<i class="fa fa-calendar-o"></i>   
              	</a>         	
           	   </li>
                    <!-- Notifications Menu -->
                    <li class="dropdown notifications-menu">
                        <!-- Menu toggle button -->
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-bell-o"></i>
                            <span class="label label-warning">${alarm.size()}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="header">You have ${alarm.size()} notifications</li>
                            <li>
                                <ul class="menu" style="max-height:400px;overflow-y:auto">
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
                            <img src="../${user.img}" class="user-image" alt=""style="width:25px;height:25px">
                            <!-- hidden-xs hides the username on small devices so only the image appears. -->
                            <span class="hidden-xs">${user.id}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-- The user image in the menu -->
                            <li class="user-header">
                                <img src="../${user.img}" class="img-circle" alt="User Image"style="width:90px;height:90px">

                            </li>
                            <!-- Menu Body -->
                            <li class="user-body">
                                <div class="row">
                                    <div class="col-xs-6 text-center" style="border-right:1px solid;">
                                        <a href="../projectmanager"><i class="fa fa-pencil-square-o"></i> Project
                                            Edit</a>
                                    </div>
                                    <div class="col-xs-6 text-center">
                                        <a href=../userInfo/${user.id}><i class="fa fa-info-circle"></i> MyInfo
                                            Edit</a>
                                    </div>

                                </div>
                                <!-- /.row -->
                            </li>
                            <!-- Menu Footer-->
                            <li class="user-footer">
                                <div class="pull-left">
                                    <a href="#" class="btn btn-default btn-flat">Profile</a>
                                </div>
                                <div class="pull-right">
                                    <a href="../logout" class="btn btn-default btn-flat">Sign out</a>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <!-- Control Sidebar Toggle Button -->
                    
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
                    <img src="../${user.img}" class="img-circle" alt="User Image"style="width:100px;height:100px">
                </div>
                <div class="pull-left info dash-user">
                    <p>${user.id}</p>
                    <!-- Status -->
                    <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
                </div>
            </div>

            <!-- Sidebar Menu -->
            <ul class="sidebar-menu">
                <li class="header"> Main Menu</li>
                <!-- Optionally, you can add icons to the links -->

                <li class="treeview active">
                    <a href="#"><i class="fa fa-user"></i><span> My Profile</span></a>
                    <ul class="treeview-menu">
                        <li class="active"><a href="#"><i class="fa fa-key"></i> Change user Info</a></li>
                       
                    </ul>
                </li>

                <li class="treeview">
                    <a href="#"><i class="fa fa-users"></i><span> Project rooms</span></a>
                    <ul class="treeview-menu">
                        <li>
                            <c:forEach var="list" items="${projects}"> 
                                <a href="../chat/${list.projectidx}" class="side-nav-button">name : ${list.name}</a>
                            </c:forEach>
                            <a href="../projectmanager"> <i class="fa fa-cogs"></i><span>Edit</span></a>
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
                Main Page
                <!-- <small>Optional description</small>-->
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
                <li class="active">Here</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="row">

                <div class="col-md-12">
                    <!-- DIRECT CHAT -->

                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">Change User Info</h3>
                        </div>
                        <!-- /.box-header -->
                        <!-- form start -->
                        <form id="registerForm" action="../register" method="POST" enctype="multipart/form-data">
                            <div class="box-body">
                                <div class="form-group">
                                    <label> ID </label>
                                    <input type="text" class="form-control" value="${user.id}" disabled>
                                    <input type="hidden" name="id" value="${user.id}">
                                </div>
                                <div class="form-group">
                                    <label>Name</label>
                                    <input type="text" class="form-control" name="name" value="${user.name}">
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputPassword1">Password</label>
                                    <input type="password" class="form-control" id="password1"
                                           name="pw" placeholder="Password">
                                </div>
                                <div class="form-group">
                                    <label for="exampleInputPassword1">Password confirm</label>
                                    <input type="password" class="form-control" id="password2"
                                           placeholder="Password">
                                </div>

                                <div class="form-group">
                                    <label for="exampleInputFile">Change image</label>
                                    <input type="file" name="file" onchange="previewImage(this,'preimage')">
                                </div>
								<div id='preimage' style="width:100px;height:100px"></div>

                            </div>
                            <!-- /.box-body -->

                            <div class="box-footer">
                                <button type="button" class="btn btn-primary" onclick="register()">Submit</button>
                            </div>
                        </form>
                    </div>
                    <!-- /.box -->


                </div>
            </div>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- Main Footer -->
    <footer class="main-footer">
        <!-- To the right -->
        <div class="pull-right hidden-xs">
            Teamkerbell
        </div>
    </footer>

    <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->

<!-- REQUIRED JS SCRIPTS -->





<!-- jQuery 2.1.4 http://ohming.tistory.com/250-->
<script src="../js/jQuery-2.1.4.min.js"></script>
<!-- Bootstrap 3.3.5 -->
<script src="../js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="../js/app.min.js"></script>
<script src="../js/prettydate.min.js"></script>
<script src="../js/previewimage.js"></script>
<script>
function register(){
    if(($("#password1").val())==($("#password2").val())){
        var form = $("#registerForm")[0];
        var formData = new FormData(form);
        $.ajax({
            url: "../register",
            type: "POST",
            data: formData,
            processData: false,
            contentType: false,
            success: function () {
               location.reload();
            }
        });
    }
}
</script>
</body>
</html>