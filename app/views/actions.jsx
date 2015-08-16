require("whatwg-fetch");

var c = {
  TODO: {
    LOAD: "LOAD_TODO",
    LOAD_INITIAL: "LOAD_INITIAL_TODO",
    ADD: "ADD_TODO"
  },

  TODO_TYPE: {
    LOAD: "LOAD_TODO_TYPE",
    ADD: "ADD_TODO_TYPE",
    REMOVE: "REMOVE_TODO_TYPE"
  },

  ROUTE: {
    TRANSITION: "ROUTE:TRANSITION"
  }
};

var methods = {

  todo: {
    load: function(){
      fetch('/todo/list')
        .then(function(response) {
          return response.json()
        }).then(function(json) {
          console.log('parsed json', json)
          this.dispatch(c.TODO.LOAD, json);
        }.bind(this)).catch(function(ex) {
          console.log('parsing failed', ex)
        })
    },
    load_initial: function(){
      fetch('/todotype/list')
        .then(function(response) {
          return response.json()
        }).then(function(json) {
          console.log('parsed json', json)
          this.dispatch(c.TODO.LOAD_INITIAL, json);
        }.bind(this)).catch(function(ex) {
          console.log('parsing failed', ex)
        })
    },
    remove: function(id) {

      var sendData = {
        id:id
      }

      fetch('/todo/delete', {
        method: 'post',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(sendData)
      }).then(function() {
        this.flux.actions.todo.load();        
      }.bind(this)).catch(function(ex) {
        console.log('deleting failed', ex)
      })
    },
    add: function(payload) {
      fetch('/todo/create', {
        method: 'post',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
      })
    }
  },

  todoType: {
    load: function(){
      fetch('/todotype/list')
        .then(function(response) {
          return response.json()
        }).then(function(json) {
          console.log('parsed json', json)
          this.dispatch(c.TODO_TYPE.LOAD, json);
        }.bind(this)).catch(function(ex) {
          console.log('parsing failed', ex)
        })
    },
    remove: function(id) {
      var sendData = {
        id:id
      }

      fetch('/todotype/delete', {
        method: 'post',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(sendData)
      }).then(function() {
        this.dispatch(c.TODO_TYPE.REMOVE, id);
      }.bind(this)).catch(function(ex) {
        console.log('error ', ex)
      })
    },

    add: function(payload) {
      fetch('/todotype/create', {
        method: 'post',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
      }).then(function() {
        this.flux.actions.todoType.load();        
      }.bind(this)).catch(function(ex) {
        console.log('error ', ex)
      })
    }
  },

  routes: {
    transition: function(path, params) {
      this.dispatch(c.ROUTE.TRANSITION, {path: path, params: params});
    }
  }
};

module.exports = {
  methods: methods,
  constants: c
};
