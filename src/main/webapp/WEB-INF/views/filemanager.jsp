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
    <!-- Bootstrap 3.3.5 -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css"
          integrity="sha384-1q8mTJOASx8j1Au+a5WDVnPi2lkFfwwEAa8hDDdjZlpLegxhjVME1fgjWPGmkzs7" crossorigin="anonymous">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
    <!-- Ionicons -->
    <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">

    <!-- Theme style -->
    <link rel="stylesheet" href="../css/wait1.css">
    <link rel="stylesheet" href="../css/jquery-jvectormap-1.2.2.css">

	 <link rel="stylesheet" href="../css/bootstrap.min.css">
	<!--tags-->
	<link href="../css/bootstrap-tokenfield.css" type="text/css" rel="stylesheet">
	<!-- end tags-->
	<!--Files-->
    <link href="../css/dataTables.bootstrap.css" type="text/css" rel="stylesheet">

    <!--[if lt IE 9]>
      <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
      <script src="http://cdnjs.cloudflare.com/ajax/libs/respond.js/1.2.0/respond.min.js"></script>
    <![endif]-->	
	
    <!-- <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script> -->
    <!--  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js" integrity="sha512-K1qjQ+NcF2TYO/eI3M6v8EiNYZfA95pQumfvcVrTHtwQVDG+aHRqLi/ETn2uB+1JqwYqVG3LIvdm9lj6imS/pQ==" crossorigin="anonymous"></script> -->
    <!--[if lt IE 9]>
    <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script><![endif]-->

	

    <link rel="stylesheet" href="../css/wait2.css">


    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
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


                    <!-- Notifications Menu -->
                    <li class="dropdown notifications-menu">
                        <!-- Menu toggle button -->
                        <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                            <i class="fa fa-bell-o"></i>
                            <span class="label label-warning" id="alarm_size">${alarm.size()}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="header" id="alarm_size_display">You have ${alarm.size()} notifications</li>
                            <li>
                                <ul class="menu" style="max-height:400px;overflow-y:auto" id="alarm">
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
                            <img src="../${user.img}" class="user-image" alt="">
                            <!-- hidden-xs hides the username on small devices so only the image appears. -->
                            <span class="hidden-xs">${user.id}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <!-- The user image in the menu -->
                            <li class="user-header">
                                <img src="../${user.img}" class="img-circle" alt="User Image">

                            </li>
                            <!-- Menu Body -->
                            <li class="user-body">
                                <div class="row">
                                    <div class="col-xs-6 text-center" style="border-right:1px solid;">
                                        <a href="../projectmanager"><i class="fa fa-pencil-square-o"></i> Project Edit</a>
                                    </div>
                                    <div class="col-xs-6 text-center">
                                        <a href="../userInfo"><i class="fa fa-info-circle"></i> MyInfo Edit</a>
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
                                    <a href="../../" class="btn btn-default btn-flat">Sign out</a>
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
                    <img src="../${user.img}" class="img-circle" alt="User Image">
                </div>
                <div class="pull-left info dash-user">
                    <p>${user.id}</p>
                    <!-- Status -->
                    <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
                </div>
            </div>

            <!-- Sidebar Menu -->
            <ul class="sidebar-menu">
                <li class="header">HEADER</li>
                <!-- Optionally, you can add icons to the links -->

                <li class="treeview active">
                    <a href="#"><i class="fa fa-user"></i><span> MyProfile</span></a>
                    <ul class="treeview-menu">
                        <li class="active"><a href=../userInfo><i class="fa fa-key"></i> Change user Info</a></li>
                    </ul>
                </li>
                <li class="treeview">
                    <a href="#"><i class="fa fa-users"></i><span> Projectrooms</span></a>
                    <ul class="treeview-menu">
                        <li>
                            <c:forEach var="list" items="${projects}"> <!-- 컨트롤러에서 넘겨받은 프로젝트를 list에 삽입 -->
                                <a href="../chat?projectIdx=${list.projectidx}"
                                   class="side-nav-button">name: ${list.name}</a>
                            </c:forEach>
                            <a href="../projectmanager"> <i class="fa fa-cogs"></i><span>Edit</span></a>
                        </li>
                    </ul>
                </li>
                <li class="treeview">
                    <a href="../courseInfo/${user.id}"><i class="fa fa-university"></i><span> Info of Courses</span></a>
                    
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

            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
                <li class="active">Here</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">

<div class="row">
<div class="col-md-10">

                    <div class="box">
                        <div class="box-header">
                          <p><strong>File manager</strong></p>
 <div class="bs-example">
                <div class="form-group" >
				 <i class="fa fa-search fa-2x pull-right" style="float:left;padding-right:26%"></i> 
                  <input type="text" class="form-control" style="width:70%;float:left" id="tokenfield-typeahead" value="red,green,blue" placeholder="Type something and hit enter for tags" />            
			   </div>
              </div>   
                         
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <table id="example2" class="table table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>Name</th>
                                    <th>Uploader</th>
                                    <th>Date</th>
									<th>Main Contents</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td>file1.txt</td>
                                    <td>sieun</td>
                                    <td>2016/12/15</td>
									<td>red,pizza,bbq</td>
                                </tr>
                                <tr>
                                    <td>image1.txt</td>
                                    <td>jun</td>
                                    <td>2016/12/15</td>
									<td>blue,Apink,bbq</td>
                                </tr>
                                <tr>
                                    <td>hangle6.txt</td>
                                    <td>hoho</td>
                                    <td>2015/11/15</td>
									<td>Twice,pink,dol</td>
                                </tr>
                                <tr>
                                    <td>siniJ6.txt</td>
                                    <td>hoho</td>
                                    <td>2015/11/15</td>
									<td>Change,KFC,CGV</td>
                                </tr>
                                <tr>
                                    <td>siniJ7.txt</td>
                                    <td>hoho</td>
                                    <td>2015/11/15</td>
									<td>Subprogram,Nuts,big</td>
                                </tr>
                                <tr>
                                    <td>siniJ8.txt</td>
                                    <td>hoho</td>
                                    <td>2015/11/15</td>
									<td>care,zare,bbq</td>
                                </tr>
                                <tr>
                                    <td>siniJ10.txt</td>
                                    <td>hoho</td>
                                    <td>2015/11/15</td>
									<td>red,Speed,wifi</td>
                                </tr>

                                <tr>
                                    <td>jolrim10.txt</td>
                                    <td>hzz</td>
                                    <td>2015/11/15</td>
									<td>olleh,5G,kt</td>
                                </tr>
                                <tr>
                                    <td>jolrim3.txt</td>
                                    <td>hzz</td>
                                    <td>2015/11/15</td>
									<td>Skt,4G,Tmembership</td>
                                </tr>
                                <tr>
                                    <td>nanem.txt</td>
                                    <td>hzz</td>
                                    <td>2015/11/15</td>
									<td>LG,4G,UPlus</td>
                                </tr>
                                <tr>
                                    <td>nanem3.txt</td>
                                    <td>hzz</td>
                                    <td>2015/11/15</td>
									<td>sin,gang,bbq</td>
                                </tr>
                                <tr>
                                    <td>jarr13.txt</td>
                                    <td>hzz</td>
                                    <td>2015/11/15</td>
									<td>samsung,sec,persnet</td>
                                </tr>
                                <tr>
                                    <td>html3.txt</td>
                                    <td>hzz</td>
                                    <td>2015/11/15</td>
									<td>game,star,Ok</td>
                                </tr>
                                <tr>
                                    <td>apple1.txt</td>
                                    <td>hzz</td>
                                    <td>2015/11/15</td>
									<td>Passion,pizza,bbq</td>
                                </tr>
                                <tr>
                                    <td>apple5.txt</td>
                                    <td>hzz</td>
                                    <td>2015/11/15</td>
									<td>kalocut,pizza,bbq</td>
                                </tr>
                                <tr>
                                    <td>banana3.txt</td>
                                    <td>hzz</td>
                                    <td>2015/11/15</td>
									<td>sudden,pizza,attack</td>
                                </tr>
                                <tr>
                                    <td>banana8.txt</td>
                                    <td>hzz</td>
                                    <td>2015/11/15</td>
									<td>gun,fight,map</td>
                                </tr>
                                <tr>
                                    <td>banana10.txt</td>
                                    <td>hzz</td>
                                    <td>2015/11/15</td>
									<td>auction,gmarket,11st</td>
                                </tr>
                                <tr>
                                    <td>delete.txt</td>
                                    <td>hzz</td>
                                    <td>2015/11/15</td>
									<td>skinfood,color,nique</td>
                                </tr>
                                <tr>
                                    <td>project.txt</td>
                                    <td>hzz</td>
                                    <td>2015/11/15</td>
									<td>melon,mnet,milk</td>
                                </tr>
                                </tbody>
                                <tfoot>
                                <tr>
                                    <th>Name</th>
                                    <th>Uploader</th>
                                    <th>Date</th>
									<th>Main Contents</th>
                                </tr>
                                </tfoot>
                            </table>
                        </div>
                        <!-- /.box-body -->
                    </div>
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
        <!-- Default to the left -->
        <!-- <strong> &copy; 2015 <a href="#">Company</a>.</strong> All rights reserved.-->
    </footer>

    <!-- Control Sidebar -->

    <div class="control-sidebar-bg"></div>
</div>
<!-- ./wrapper -->


<!-- jQuery 2.1.4 -->
<script src="../js/jQuery-2.1.4.min.js"></script>
<!-- Bootstrap 3.3.5 -->
<script src="../js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="../js/app.min.js"></script>

<!--Table-->
<script src="../js/jquery.dataTables.min.js"></script>
<script src="../js/dataTables.bootstrap.min.js"></script>
<!-- tag -->
 <script type="text/javascript" src="../js/bootstrap-tokenfield.js" charset="UTF-8"></script>
   <script type="text/javascript" src="../js/typeahead.bundle.min.js" charset="UTF-8"></script>
   <script type="text/javascript" src="../js/docs.min.js" charset="UTF-8"></script>
<script>
$(function () {
    $("#example1").DataTable();
    $('#example2').DataTable({
      "paging": true,
      "lengthChange": false,
      "searching": false,
      "ordering": true,
      "info": true,
      "autoWidth": false
    });
  });
</script>
</body>
</html>