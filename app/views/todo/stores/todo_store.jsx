var Fluxxor = require("fluxxor");

var actions = require("../../actions.jsx");

var TodoStore = Fluxxor.createStore({
  initialize: function() {

    this.todos = [];
    this.todoTypes = [];

    this.bindActions(
      actions.constants.TODO.LOAD_INITIAL, this.loadInitialData,
      actions.constants.TODO.LOAD, this.load
    );
  },

  loadInitialData: function(data) {
    this.todoTypes = data;
    this.emit("change");
  },

  load: function(data) {
    this.todos = data;
    this.emit("change");
  },

  getState: function() {
    return {
        todos: this.todos,
        todoTypes: this.todoTypes
    };
  },
});

module.exports = TodoStore;
