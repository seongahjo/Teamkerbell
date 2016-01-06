/*!
 * AddTags.js
 * Copyright (c) Matsukaze. All rights reserved.
 * @author mach3
 * @version 1.2.2
 * @requires jQuery 1.4 or later
 */


var AddTags = function( option, tags ){
	tags = tags || [];
	option = option || {};
	this.setTags( tags );
	this.config( option );
};
/**
 * Class help us to create "add tag" interface easily.
 * @class
 * @param {array} tags Tags for default
 */
AddTags.prototype = {
	EVENT_CHANGE : "change",
	EVENT_ERROR : "error",
	tags : [],
	option : {
		input : "#tagInput",
		addButton : "#tagAddButton",
		clearButton : "#tagClearButton",
		list : "#tagList",
		deleteButton : "#tagList .delete"
	},
	/**
	 * Set default tag collection
	 * @param {array} tags Tags for default
	 * @return {object} AddTags object
	 */
	setTags : function( tags ){
		this.tags = tags || [];
		return this;
	},
	/**
	 * Set configuration option
	 * @param {object} option Configuration option
	 * @return {object} AddTags object
	 */
	config : function( option ){
		this.option = $.extend( {}, this.option, option );
		return this;
	},
	/**
	 * Initialize the UI
	 * @return {object} AddTags object
	 */
	run : function(){
		$(this.option.input).bind( "keypress", $.proxy( this._onKeyPress, this ) );
		$(this.option.addButton).bind( "click", $.proxy( this._onClickAdd, this ) );
		$(this.option.clearButton).bind( "click", $.proxy( this.clear, this ) );
		$(this.option.deleteButton).on( "click", $.proxy( this._onClickDelete, this ) );
		this.refreshList();
		return this;
	},
	_onKeyPress : function( e ){
		if( e.keyCode !== 13 ) return;
		this._onClickAdd();
	},
	_onClickAdd : function(){
		var input = $(this.option.input);
		this.add( input.val() );
		input.val("");
	},
	_onClickDelete : function( e ){
		this.remove( $(e.target).attr("data-tag") );
	},
	/**
	 * Add tag(s) to the list
	 * @param {string} tag Simple string for tag, or tag collection separated with comma.
	 * @return {object} AddTags object
	 */
	add : function( tag ){
		var _this, changed;
		_this = this;
		changed = false;
		tag = tag.replace( /^\s+|\s+$/, "" ).split(",");
		$.each( tag, function( i, t ){
			if( ! t.length ) return;
			t = $("<span>").text(t).html();
			_this.tags.push( t );
			_this.tags.sort();
			_this.tags = $.unique( _this.tags ).sort();
			changed = true;
		});
		if( changed ){ $(this).trigger( this.EVENT_CHANGE ); }
		this.refreshList();
		return this;
	},
	/**
	 * Remove a tag from the list
	 * @param {string} tag Tag to remove.
	 * @return {object} AddTags object
	 */
	remove : function( tag ){
		this.tags = $.grep( this.tags, function( v, i ){ return v !== tag; } );
		this.refreshList();
		$(this).trigger( this.EVENT_CHANGE );
		return this;
	},
	/**
	 * Clear all tags
	 * @return {object} AddTags object
	 */
	clear : function(){
		this.tags = [];
		this.refreshList();
		$(this).trigger( this.EVENT_CHANGE );
		return this;
	},
	/**
	 * Refresh the display of ul element containing tags
	 * @return `object} AddTags object
	 */
	refreshList : function(){
		var html, list;
		html = '<li>{{tag}} '
		+ '<input type="button" value="X" class="delete" data-tag="{{tag}}" /></li>';
		list = $(this.option.list).html("");
		this.tags.sort();
		$.each( this.tags, function( i, t ){
			$( html.replace( /{{tag}}/g, t ) ).appendTo( list );
		});
		return this;
	},
	/**
	 * Return tag collection as string separated with comma.
	 * @return {strgin} Tag collection
	 */
	toString : function(){
		return this.tags.join(",");
	},
	/**
	 * Wrapper of jQuery.bind
	 */
	bind : function( name, func ){
		return $(this).bind( name, func );
	},
	/**
	 * Wrapper of jQuery.unbind 
	 */
	unbind : function( name, func ){
		return $(this).unbind( name, func );
	},
	/**
	 * Error handler
	 */
	_error : function( msg ){
		$(this).trigger( this.EVENT_ERROR, msg );
	}
};

