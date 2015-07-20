var Fluxxor = require("fluxxor");

var actions = require("../../actions.jsx");

var TodoTypeStore = Fluxxor.createStore({
  initialize: function() {

    this.todoTypes = [];

    this.bindActions(
      actions.constants.TODO_TYPE.LOAD, this.load,
      actions.constants.TODO_TYPE.ADD, this.add,
      actions.constants.TODO_TYPE.REMOVE, this.onRemoveTodoType
    );
  },

  add: function(payload) {
    $.ajax({
      url: '/todotype/create',
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

  onRemoveTodoType: function(id) {
    var sendData = {
      id:id
    }

    $.ajax({
      url: '/todotype/delete',
      dataType: 'json',
      method: 'POST',
      data: sendData,
      success: function(data) {
        this.load();
        this.emit("change");
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(status, err.toString());
      }.bind(this)
    });
  },

  load: function() {
    $.ajax({
      url: '/todotype/list',
      dataType: 'json',
      success: function(data) {
        this.todoTypes = data;
        this.emit("change");
      }.bind(this),
      error: function(xhr, status, err) {
        console.error(status, err.toString());
      }.bind(this)
    });
  },

  getState: function() {
    return {
      todoTypes: this.todoTypes
    };
  },
});

module.exports = TodoTypeStore;
