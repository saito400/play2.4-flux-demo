var Fluxxor = require("fluxxor");

var actions = require("../../actions.jsx");

var TodoTypeStore = Fluxxor.createStore({
  initialize: function() {

    this.todoTypes = [];

    this.bindActions(
      actions.constants.TODO_TYPE.LOAD, this.load,
      actions.constants.TODO_TYPE.REMOVE, this.onRemoveTodoType
    );
  },

  onRemoveTodoType: function(id) {
    this.todoTypes = this.todoTypes.filter(x => x.id != id);
    this.emit("change");
  },

  load: function(data) {
    this.todoTypes = data;
    this.emit("change");
  },

  getState: function() {
    return {
      todoTypes: this.todoTypes
    };
  },
});

module.exports = TodoTypeStore;
