var Fluxxor = require("fluxxor");

var actions = require("../../actions.jsx");

var TodoStore = Fluxxor.createStore({
  initialize: function() {

    this.todos = [];
    this.todoTypes = [];

    this.bindActions(
      actions.constants.TODO.LOAD_INITIAL, this.loadInitialData,
      actions.constants.TODO.LOAD, this.load,
      actions.constants.TODO.ADD, this.onAdd,
      actions.constants.TODO.REMOVE, this.onRemove
    );
  },

  onAdd: function(payload) {
    $.ajax({
      url: '/todo/create',
      dataType: 'json',
      method: 'POST',
      data: payload,
      success: function(data) {
        this.load();
        this.emit("change");
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(status, err.toString());
      }.bind(this)
    });
  },

  onRemove: function(id) {
    var sendData = {
      id:id
    }
    $.ajax({
      url: '/todo/delete',
      dataType: 'json',
      method: 'POST',
      data: sendData,
      success: function(data) {
        this.getTodoTypes();
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(status, err.toString());
      }.bind(this)
    });
  },

  loadInitialData: function() {
    $.ajax({
      url: '/todotype/list',
      dataType: 'json',
      success: function(data) {
        this.todoTypes = data;
        this.emit("change");
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(this.props.url, status, err.toString());
      }.bind(this)
    });
  },

  load: function() {
    $.ajax({
      url: '/todo/list',
      dataType: 'json',
      success: function(data) {
        this.todos = data;
        this.emit("change");
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(status, err.toString());
      }.bind(this)
    });
  },

  getState: function() {
    return {
        todos: this.todos,
        todoTypes: this.todoTypes
    };
  },
});

module.exports = TodoStore;
