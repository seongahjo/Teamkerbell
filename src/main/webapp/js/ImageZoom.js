/*
	- Load images and store as array
*/


// jQuery Plugin
(function($){
	var $overlay;
	var $lightbox;
	var $caption;
	
	var images = [];
	var currentIndex = -1;
	var totalImages = 0;
	
	var viewport = {width:$(window).width(), height:$(window).height()};
	
	var origin = {};
	var $thumb;
	var isGallery = false;
	var containerHTML = "<div id=\"overlay\"></div><div id=\"lightbox\"><span class=\"caption\"></span></div>";
	var peakWidth = 60;
	
	$.fn.imageZoom = function(options){
		var _this = this;
		var _options;
		
		var init = function (options){
			// Add lightbox container DOM
			$("body").append(containerHTML);
			
			$overlay = $("#overlay");
			$lightbox = $("#lightbox");
			$caption = $lightbox.children("span.caption");
			
			_options = $.extend({
				scale : 0.85,
				transition: 300
			}, options);
			
			// Add event listeners
			_this.on("click", initImage);
			$lightbox.on("click", exitZoom);
			$(document).keyup(zoomKeys);
			
			$(window).resize(function(e){
				viewport = {width:$(window).width(), height:$(window).height()};
				updateOrigin($("img.zoomActive"));
				
				// resize images
				$lightbox.find("img").each(function(){
					$this = $(this);
					var img = $this[0];
					
					var prefer = img.width/img.height < viewport.width/viewport.height ? "h" : "w";
					var img_w = prefer == "w" ? viewport.width * _options.scale : viewport.height * _options.scale * (img.width/img.height);
					var img_h = prefer == "h" ? viewport.height * _options.scale : viewport.width * _options.scale * (img.height/img.width);
					img_w = Math.min(img_w , img.naturalWidth);
					img_h = Math.min(img_h , img.naturalHeight);
					var W = Math.round(img_w)+"px";
					var H = Math.round(img_h)+"px";
					var marginLeftPrev 		= peakWidth - img_w + "px";
					var mT = -Math.round(img_h/2)+"px";
					var mL;
					
					if ($this.hasClass("prev")){
						mL = marginLeftPrev;
					} else if ($this.hasClass("next")){
						mL = -peakWidth+"px";
					} else if ($this.hasClass("current")){
						mL = -Math.round(img_w/2)+"px";
					}
					
					$this.css({
						width	: W,
						height	: H,
						"margin-left"	: mL,
						"margin-top"	: mT
					}).data("margin-left", marginLeftPrev);
				});
			});
		}
		
		var updateOrigin = function ($thumb){
			origin.offset	= $thumb.offset();
			origin.size		= {width:$thumb.width(), height:$thumb.height()};
		}
		
		var prepareGallery = function ($image_links){
			$image_links.each(function(i){
					var url = this.href;
					var title = $(this).attr("title") ? $(this).attr("title") : $(this).data("title") ? $(this).data("title") : null;
					prepareImage(url, false, i, title);
			});
		}
		
		var initImage = function(e){
			e.preventDefault();
			// image thumbnail
			$thumb = $(e.target);
			updateOrigin($thumb);
			$thumb.addClass("flicker zoomActive");
			
			// original image url
			var url = this.href;
			var title = $(this).data("title");
			
			isGallery = $(this).parent().hasClass("gallery");
			currentIndex = $(this).index();
			
			var $gallery = $(this).parent();
			$gallery.addClass("zoomActive");
			totalImages = isGallery ? $gallery.children("a").length : 1;
			
			prepareGallery($gallery.children("a"));
		}
		var prepareImage = function(url, animate, index, title){
			var image = new Image();
			var _index = index;
			var _animate = animate;
			var _title = title;
			$(image).on("load",{
					img		:image,
					index	: _index,
					title	: _title,
					animate	: _animate
				}
				, storeImages);
			image.src = url;
		}
		
		var storeImages = function (img){
			var imageIndex = img.data.index;
			var prevIndex = Math.max(0, currentIndex - 1);
			var nextIndex = Math.min(totalImages, currentIndex + 1);
			images[imageIndex] = img;
			if (imageIndex == prevIndex || imageIndex == currentIndex || imageIndex == nextIndex)
				showImage(img);
		}
		
		var showImage = function (imageObject){
			var e = imageObject;
			var img = e.data.img;
			var index = e.data.index;
			var title = e.data.title;
			
			var prefer = img.width/img.height < viewport.width/viewport.height ? "h" : "w";
			var img_w = prefer == "w" ? viewport.width * _options.scale : viewport.height * _options.scale * (img.width/img.height);
			var img_h = prefer == "h" ? viewport.height * _options.scale : viewport.width * _options.scale * (img.height/img.width);
			img_w = Math.min(img_w, img.naturalWidth);
			img_h = Math.min(img_h, img.naturalHeight);
			var width	= Math.round(img_w);
			var height	= Math.round(img_h);
			
			
			var $this = $(img);
			var mL = (peakWidth - width) + "px";
			$this.data({"margin-left":mL, caption: title}).click(navigateByImage);
			switch (index){
				case currentIndex:
					var cssFrom = {
					"width"		: $thumb.width(),
					"height"	: $thumb.height()
					};
					
					var cssTo = {
						width	: width+"px",
						height	: height+"px",
						left	: "",
						top		: "",
						"margin-left"	: -Math.round(img_w/2)+"px",
						"margin-top"	: -Math.round(img_h/2)+"px",
					};
					
					$thumb.removeClass("flicker");
					$this.css(cssFrom)
						 .offset({left: origin.offset.left, top: origin.offset.top - $(window).scrollTop()})
						 .appendTo($lightbox);
					
					$overlay.addClass("show");
					$thumb.removeClass("flicker");
					$lightbox.show();
					
					setTimeout(function(){					
						$this.css(cssTo).addClass("current");
						$caption.addClass("show").text(title).css("margin-top", height * 0.5 + 8 + "px");
					},100);
				break;
				
				case currentIndex - 1:
					$this.css(
						{
							width	: width+"px",
							height	: height+"px",
							"margin-left"	: -width,
							"margin-top"	: -Math.round(img_h/2)+"px",
						}
					).appendTo($lightbox).addClass("prev show");
					
					setTimeout(function(){$this.css("margin-left", mL)}, 100);
					
				break;
				
				case currentIndex + 1:
					$this.css(
						{
							width	: width+"px",
							height	: height+"px",
							"margin-left"	: 0,
							"margin-top"	: -Math.round(img_h/2)+"px",
						}
					).appendTo($lightbox).addClass("next show");
					setTimeout(function(){$this.css("margin-left",  -peakWidth +"px")}, 100);
				break;
			}
		}
		
		var exitZoom = function (e) {
			if(e != undefined && e.target.nodeName == "IMG" && !$(e.target).hasClass("current"))
				return false;
			
			$target = $lightbox.find("img.current");
			var $imgs = $lightbox.find("img");
			var prevW = $("img.prev").width();
			$imgs.filter(".next").removeClass("showw").css({"margin-left":""});
			$imgs.filter(".prev").removeClass("showw").css({"margin-left":-prevW+"px"});
			$caption.removeClass("show");
			$target.css({
				width	: origin.size.width + "px",
				height	: origin.size.height + "px",
				left	: origin.offset.left,
				top		: origin.offset.top - $(window).scrollTop(),
				"margin-left"	: 0,
				"margin-top"	: 0,
			});
			
			$overlay.removeClass("show");
			setTimeout(function(){
				$(".zoomActive").removeClass("zoomActive");
				$imgs.removeClass().remove();
				$lightbox.hide();
			}, _options.transition);
		}
		
		var navigateByImage = function (e) {
			e.stopPropagation();
			if ($(this).hasClass("prev"))
				movePage("prev");
			else if ($(this).hasClass("next"))
				movePage("next");
			else 
				exitZoom(e);
		}
		var movePage = function (direction){
			var $current = $("img.current");
			var $target;
			var $next;
			$caption.removeClass("show");
			switch (direction){
				case "next":
					currentIndex++;
					$("img.prev").remove();
					$target = $("img.next").removeClass("next");
					$current.addClass("prev show").css({"margin-left" : $current.data("margin-left")});
					if (currentIndex < images.length -1)
						showImage(images[currentIndex+1]);
				break;
				
				case "prev":
					currentIndex--;
					$("img.next").removeClass().remove();
					$target = $("img.prev").removeClass("prev");
					$current.addClass("next show").css({"margin-left" : -peakWidth+"px"});
					if (currentIndex > 0)
						showImage(images[currentIndex-1]);
				break;
			}
			
			$current.removeClass("current");
			$target.addClass("current");
			$caption.text($target.data("caption")).css("margin-top", $target.height() * 0.5 + 8 + "px");
			
			// Re-position image
			var w = Number($target.css('width').replace(/[^\d\.]/g, '')) * 0.5;
			$target.css({left:"", "margin-left": -w +"px"});
			
			$thumb.removeClass("zoomActive");
			$thumb = $(".gallery.zoomActive a:eq("+ currentIndex +") img");
			$thumb.addClass("zoomActive");
			updateOrigin($thumb);
			
			setTimeout(function(){$caption.addClass("show")}, 100);
		}
		
		var zoomKeys = function (e) {
			if($overlay.is(":visible")){
				switch(e.which){
					// ESC
					case 27:
						exitZoom();
					break;
					
					//j, →
					case 74:
					case 39:
						if( totalImages - 1 > currentIndex)
							movePage("next");
					break;
					
					// k, ←
					case 75:
					case 37:
						if( 0 < currentIndex)
							movePage("prev");
					break;
				}
			}
		}
		
		init(options);
		
		return this;
	}
	
}(jQuery));