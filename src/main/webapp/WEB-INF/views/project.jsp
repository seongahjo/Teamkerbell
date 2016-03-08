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
    <link rel="stylesheet" href="../css/wait2.css">
    <!-- daterange picker -->
    <link rel="stylesheet" href="../css/daterangepicker-bs3.css">
    <link rel="stylesheet" href="../css/bootstrap-timepicker.min.css">
    <link rel="stylesheet" href="../css/dataTables.bootstrap.css">
    <!-- Select2 -->
    <link rel="stylesheet" href="../css/select2.min.css">
    <!-- HTML5 Shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="http://html5shim.googlecode.com/svn/trunk/html5.js"></script>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->

    <!--gallery-->
    <link rel="stylesheet" href="../css/ImageZoom.css"/>

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
               
            <div class="navbar-custom-menu">
                <ul class="nav navbar-nav">
               
                <li data-toggle="modal" data-target="#photoModal">
            	<a data-toggle="tooltip" title=""  data-placement="bottom" data-original-title="Images Gallery">
              	<i class="fa fa-picture-o"></i>
            	</a>
            	</li>
            	
            	
            	<li data-toggle="modal" data-target="#todoList" >
            	<a data-toggle="tooltip" title=""  data-placement="bottom" data-original-title="to do List">
              	<i class="fa fa-edit"></i>
            	</a>
            	</li>

            	 <li  onclick="location.href='../filemanager/${project.projectidx}';">
            	<a data-toggle="tooltip" title="" data-placement="bottom"data-original-title="File Manager">
              	<i class="fa fa-file-text-o"></i>
            	</a>
            	
            	</li>
            	
            	 <li onclick="location.href='../calendar/${project.projectidx}';">
            	<a data-toggle="tooltip" title="" data-placement="bottom"data-original-title="Calendar">
              	<i class="fa fa-calendar-o"></i>
            	</a>
            	</li>
                    <li class="dropdown notifications-menu">
                        <a href="#" class="dropdown-toggle" data-toggle="dropdwon" >
                            <i class="fa fa-bell-o"  id="alarm"></i>
                            <span class="label label-warning" id="alarm-size">${alarm.size()}</span>
                        </a>
                        <ul class="dropdown-menu">
                            <li class="header" id="alarm-content" >You have ${alarm.size()} notifications</li>
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
                                <img src="../${user.img}" class="img-circle" alt="User Image" style="width:90px;height:90px">

                            </li>
                            <!-- Menu Body -->
                            <li class="user-body">
                                <div class="row">
                                    <div class="col-xs-6 text-center" style="border-right:1px solid;">
                                        <a href="../projectmanager"><i class="fa fa-pencil-square-o"></i> Project Edit</a>
                                    </div>
                                    <div class="col-xs-6 text-center">
                                        <a href=../userInfo/${user.id}><i class="fa fa-info-circle"></i> MyInfo Edit</a>
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
                    <img src="../${user.img}" class="img-circle main-img" alt="User Image" style="width:100px;height:100px">
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
                        <li class="active"><a href=../userInfo/${user.id}><i class="fa fa-key"></i> Change user Info</a></li>
                      
                    </ul>
                </li>

                <li class="treeview active">
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
                Project Room
                <small> ${project.name}</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> Mainpage</a></li>
                <li class="active">Here</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="row">
                <div class="col-md-7">
                    <div class="box box-primary direct-chat direct-chat-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">Chatting</h3>

                            <div class="box-tools pull-right">
                                <span data-toggle="tooltip" title="New Messages" class="badge bg-light-blue"></span>
                                <button type="button" class="btn btn-box-tool" data-widget="collapse"><i
                                        class="fa fa-minus"></i>
                                </button>
                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body">
                            <!-- Conversations are loaded here -->
                            <div class="direct-chat-messages chatbox" id="chat">
                            </div>
                        </div> <!-- box body-->
                    </div> <!-- box -->

                    <!-- /.box-body -->
                    <div class="box-footer">
                        <div class="input-group">
                            <input type="text" id="typing" name="message" placeholder="Type Message ..."
                                   class="form-control" onkeypress="if(event.keyCode==13)sendMsg()">
                          <span class="input-group-btn">
                            <button type="button" type="button" class="btn btn-primary btn-flat" onClick="sendMsg()">
                                Send
                            </button>
                            <span onclick="selectFile()" >
                            <button type="button" class="btn btn-default btn-sm" data-toggle="tooltip" title="" data-original-title="File Upload" style="margin-left:3px;height:34px">
                 			 <i class="fa fa-paperclip fa-2x"></i></button>
                 			 </span>
                 			  <span data-toggle="modal" data-target="#todoMadal" >
                 			  <button type="button" class="btn btn-default btn-sm" data-toggle="tooltip" title="" data-original-title="Add TodoList" style="margin-left:3px;height:34px">
                 			 <i class="fa fa-pencil-square-o fa-2x"></i></button>
                 			 </span>
                          </span>
                        </div>
                    </div>
                    <!-- /.box-footer-->
                </div>    <!-- col -->


                <!-- memo -->

                <section class="col-md-3 connectedSortable">

                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">Memo</h3>
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
                                <textarea id="memo" class="form-control" style="height: 300px"
                                          disabled>${project.minute}</textarea>
                            </div>

                        </div>
                        <!-- /.box-body -->
                        <div class="box-footer">
                            <div class="pull-right">
                                <button id="writebutton" type="button" class="btn btn-default" onClick="write_memo()"><i
                                        class="fa fa-pencil"></i> Writing
                                </button>
                                <button id="savebutton" type="button" class="btn btn-primary hidden"
                                        onClick="save_memo()"><i class="fa fa-floppy-o"></i> Save
                                </button>
                            </div>
                            <button type="reset" class="btn btn-default"><i class="fa fa-times"></i> Discard</button>
                        </div>
                        <!-- /.box-footer -->
                    </div>
                    <!-- /. box -->
                </section>


                <div class="col-md-2">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">Users State</h3><i class="fa fa-user-plus fa-3x pull-right"
                                                                     data-toggle="modal" data-target="#InviteUser"></i>

                            <div class="box-tools pull-right">

                            </div>
                        </div>
                        <!-- /.box-header -->
                        <div class="box-body state-body">
                            <ul class="products-list product-list-in-box">
                                <c:forEach var="list" items="${users}">
                                    <li class="item">
                                        <img src="../${list.img}" alt="User Image"style="width:30px;height:30px">
                                        <a class="users-list-name user-st-name" href="#">${list.id}</a>
                                        <i id="user${list.id}on"
                                           class="fa fa-circle st-cir text-success pull-right hidden"
                                        ></i>
                                        <i id="user${list.id}off"
                                           class="fa fa-circle st-cir text-warning pull-right"></i>
                                    </li>
                                </c:forEach>

                                <!-- /.item -->
                            </ul>
                        </div>
                        <!-- /.box-body -->
                        <div class="box-footer text-center">

                        </div>
                        <!-- /.box-footer -->
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


    <div class="control-sidebar-bg"></div>
</div>
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


                                <input type="text" id="fakeFileTxt" class="fakeFileTxt" readonly="readonly" multiple>
                                <div class="fileDiv">
                                    <input type="button" value="Select File" onclick="selectFile()" class="buttonImg"/>
                                    <input type="file" id="file" class="realFile" onChange="upload()" name="File[]"/>
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
                <li>
                 <img src="../${user.img}"  class="img-circle img-bordered-sm"  alt="user image">
                        <span class="username">
                          <span>${user.id}</span>
                        </span>
                  <span class="text" >Design a nice theme</span>
                  <!-- Emphasis label -->
                  <small class="label label-danger"><i class="fa fa-clock-o"></i> 2 mins</small>
                  <!-- General tools such as edit or delete-->
                  
                </li>
                <li>
                 <img src="../${user.img}" class="img-circle img-bordered-sm"  alt="user image">
                        <span class="username">
                          <span>${user.id}</span>
                        </span>
                  <span class="text" >Design a nice theme</span>
                  <!-- Emphasis label -->
                  <small class="label label-danger"><i class="fa fa-clock-o"></i> 2 mins</small>
                  <!-- General tools such as edit or delete-->
                  
                </li>
                <li class="done">
                 <img src="../${user.img}"  class="img-circle img-bordered-sm"  alt="user image">
                        <span class="username">
                          <span>${user.id}</span>
                        </span>
                  <span class="text" >Design a nice theme</span>
                  <!-- Emphasis label -->
                  <small class="label label-danger"><i class="fa fa-clock-o"></i> 4 mins</small>
                  <!-- General tools such as edit or delete-->
                  
                </li>
                <li class="done">
                 <img src="../${user.img}"   class="img-circle img-bordered-sm"  alt="user image">
                        <span class="username">
                          <span>${user.id}</span>
                        </span>
                  <span class="text" >Design a nice theme</span>
                  <!-- Emphasis label -->
                  <small class="label label-danger"><i class="fa fa-clock-o"></i> 2 mins</small>
                  <!-- General tools such as edit or delete-->                 
                </li>
                
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
                        <img src="../loadImg?name=${list.storedname}" width="170" height="120" alt="An elegant profile" style="margin-top:3%;margin-right:1%">
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
                            <label class="control-label" for="inputSuccess"><i class="fa fa-check"></i> List what you
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


<div class="modal fade" id="InviteUser" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="exampleModalLabel">Invite User</h4>
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

                        <!--  if user's info doesn't exist
                        <div style="text-align:center;">
                         <img src="cry.png"  width="50%" height="200px">
                         <p> User Info doesn't exist</p>
                         </div>-->
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
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
<script src="../js/alarm.js"></script>
<script src="../js/socket.io.js"></script>
<script src="../js/prettydate.min.js"></script>
<script src="../js/jquery.filepicker.js"></script>

<!-- gallery--><
<script type="text/javascript" src="../js/ImageZoom.js"></script>
<script>
    var scheduleStart;
    var scheduleEnd;
    var inviteU;
    $(function () {

        $("a.zoom").imageZoom({scale: 0.75});
        //Initialize Select Elements
        $(".select2").select();
        //Date range picker
        $('#reservation').daterangepicker({}, function (start, end) {
            scheduleStart = start;
            scheduleEnd = end;
        });
        //Date range picker with time picker
        $('#reservationtime').daterangepicker({timePicker: true, timePickerIncrement: 30, format: 'YYYY-MM-DD h:mm A'});
        //Date range as a button
        $('#daterange-btn').daterangepicker(
                {
                    ranges: {
                        'Today': [moment(), moment()],
                        'This Month': [moment().startOf('month'), moment().endOf('month')]
                    },
                    startDate: moment().subtract(29, 'days'),
                    endDate: moment()
                },
                function (start, end) {
                    $('#reportrange span').html(start.format('YYYY-MM-DD') + ' - ' + end.format('YYYY-MM-DD'));
                }
        );
        $(".timepicker").timepicker({
            showInputs: false
        });
        $('#InviteUser').on('hidden.bs.modal', function (e) {
            $("#user").html('');
            $("#inviteForm #inviteId").val('');

        })
    });
    var option = "Today";
    var Tminute = "${project.minute}";
    var socket;
    $(document).ready(function () {
        socket = io.connect("http://localhost:9999");
        socket.emit('join', {
            projectIdx: "${project.projectidx}",
            userIdx:${user.useridx},
            userName: "${user.name}",
            userId: "${user.id}",
            userImg: "${user.img}"
        });
        socket.on('response', function (data) {
            console.log(data);
            if (data.user == "${user.id}") {
                $("#chat").append('<div class="direct-chat-msg right"> <div class="direct-chat-info clearfix"> <span class="direct-chat-name pull-right">' + data.user + '</span> </div> <img class="direct-chat-img" src=../' + data.img + ' alt="message user image"> <div class="direct-chat-text pull-right"> ' + data.msg + '</div> </div> <span class="direct-chat-timestamp pull-right" >' + data.date + '</span><br>');
            }
            else
                $("#chat").append('<div class="direct-chat-msg"> <div class="direct-chat-info clearfix"> <span class="direct-chat-name pull-left">' + data.user + '</span> </div> <img class="direct-chat-img" src=../' + data.img + ' alt="message user image"> <div class="direct-chat-text pull-left"> ' + data.msg + '</div> </div>  <span class="direct-chat-timestamp pull-left ts-left" >' + data.date + '</span><br>');

            <!--  $('#didiv').scrollTop($('#didiv')[0].scrollHeight);-->
            $('#chat').scrollTop($('#chat')[0].scrollHeight);
        });
        socket.on('write', function (flag) {
            if (flag == "yes") {
                $("#memo").prop("disabled", false);
                $("#writebutton").addClass("hidden");
                $("#savebutton").removeClass("hidden");
            }
            else {
                $("#memo").prop("disabled", true);

            }
        });
        socket.on('adduser', function (id) {
            $("#user" + id + "off").addClass("hidden");
            $("#user" + id + "on").removeClass("hidden");
        });
        socket.on('deleteuser', function (id) {
            $("#user" + id + "off").removeClass("hidden");
            $("#user" + id + "on").addClass("hidden");
        });
        socket.on('refresh', function (memo) {
            $("#memo").val(memo);
            Tminute = memo;
        });
        socket.on('alarm', function (data) {
                var par="userIdx="+${user.useridx};
                $.ajax({
                    url: "../updateAlarm",
                    data: par,
                    dataType: 'json',
                    type: 'GET',
                    success: function (data) {
                        var size=parseInt($("#alarm-size").text())+1;
                        $("#alarm").effect("bounce",{direction:'left',distance:13, times:3},500);
                        $("#alarm-size").text(size);
                        $("#alarm-content").text('You have '+size+'notifications');
                        $("#alarm-list").prepend('<li id="alarm-"'+data.alarmidx+'><a href="#">'+
                                                '<i class="fa fa-users text-aqua"></i><strong>'+data.actorid+'</strong>'+
                                                'has invited you to <strong>'+data.projectname+'</strong>'+
                                                '<div style="float:right;">'+
                                                   ' <button type="button" class="btn btn-primary btn-xs"'+
                                                            'onclick=accept("'+data.alarmidx+'")>Ok</button>'+
                                                    '<button type="button" class="btn btn-default btn-xs"'+
                                                            'onclick=decline("'+data.alarmidx+'")>Cancel'+
                                                    '</button>'+
                                                '</div>'+
                                            '</a>'+
                                        '</li>');
                    }
                });
        });
        $('#file').hover(function (event) {
            $('#file_over').addClass('front_hover');
        }, function () {
            $('#file_over').removeClass('front_hover');
        });

    }); // document function
    function save_memo() {
        socket.emit('save', {memo: $("#memo").val()});
        Tminute = $("#memo").val();
        $("#writebutton").removeClass("hidden");
        $("#savebutton").addClass("hidden");
        $("#memo").prop("disabled", true);
        $("#selectBox").attr("disabled", false);
    }

    function write_memo() {
        if (option == "Today") {
            socket.emit('writer');
            $("#selectBox").attr("disabled", true);
        }
    }

    $('#memo').keyup(function (event) {
        if (event.keyCode != 8)
            socket.emit('refreshToAll', {memo: $("#memo").val()});
    });

    function sendMsg() {
        var dates = new Date().toShortTimeString();
        socket.emit('msg', {msg: $("#typing").val(), date: new Date().toString('HH:mm')});
        $("#typing").val("");
        $('#chat').scrollTop($('#chat')[0].scrollHeight);
    }

    function invite() {
        var par = "userId=" + inviteU;
        $.ajax({
            url: "../inviteUser",
            data: par,
            dataType: 'text',
            async: true,
            processData: false,
            contentType: false,
            type: 'GET',
            success: function (data) {
                socket.emit('invite', {userIdx:data});
                console.log(data+"Invite");
                $("#InviteUser").modal('hide');
            }
        });
    }
    function search() {
        var par = "userId=" + $("#inviteForm #inviteId").val();
        $.ajax({
            url: "../inviteUser",
            type: 'POST',
            dataType: 'json',
            data: par,
            success: function (data) {
                inviteU = data.userId;
                $("#user").html('<div class="box box-primary" style="width:70%; margin-left:15%; margin-top:5%"> <div class="box-body box-profile"> <img class="profile-user-img img-responsive img-circle" src="'+"../"+ data.img + '"alt="User profile picture"> <h3 class="profile-username text-center">' + data.userId + '</h3> <p class="text-muted text-center">' + data.name + '</p><a href="#" class="btn btn-primary btn-block" onclick="invite()"><b>Invite</b></a></div> </div>');
            },
            error : function(){
                $("#user").html('<div style="text-align:center;"> <img src="../img/cry.png"  width="50%" height="200px"> <p> User Info doesnt exist</p> </div>');
            }
        });
    }
    /*
     socket 관련함수 끝
     */
    function test() {
        var file = $("#file")[0].files[0];
        $("#fakeFileTxt").val(file.name);
    }

    function selectFile() {
        document.getElementById("file").click();
    }

    $("#selectBox").change(function () {
        option = $(this).children("option:selected").text();
        if (option == "Today")
            $("#memo").val(Tminute);
        else
            $("#memo").val($(this).children("option:selected").val());
    });
    //11시 59분에 저장하고 새로고침하는 함수 추가예정


    $("#inviteId").keydown(function (key) {
        if (key.keyCode == 13) {
            search();
        }
    });


    function makeTodolist() {

        var param = "projectIdx=${project.projectidx}&userId=" + $("#todoselect").children("option:selected").val()
                + "&startdate=" + scheduleStart.format('YYYY-MM-DD') + "&enddate=" + scheduleEnd.format('YYYY-MM-DD')
                + "&content=" + $("#todocontent").val();
        $.ajax({
            url: "../makeTodolist",
            data: param,
            dataType: 'text',
            async: true,
            processData: false,
            contentType: false,
            type: 'GET',
            success: function (response) {
                location.reload();
            },
            error: function () {
            }
        });

    }
    function endsWith(str, suffix) {
        return str.indexOf(suffix, str.length - suffix.length) !== -1;
    }
    function upload() {
        var form = $("#uploadForm")[0];
        var formData = new FormData(form);
        $.ajax({
            url: "../file",
            type: "POST",
            dataType: "json",
            data: formData,
            processData: false,
            contentType: false,
            success: function (data) {
                    socket.emit("file", {msg: data, user: "${user.name}", date: new Date().toString('HH:mm'),type:data.type});
                else
                    socket.emit("file", {msg: data, user: "${user.name}", date: new Date().toString('HH:mm'),type:data.type});
                $("#uploadModal").modal('hide');
            }
        });
    }

</script>
</body>
</html>