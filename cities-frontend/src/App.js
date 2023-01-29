import {Component} from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import 'bootstrap/dist/js/bootstrap.min.js';
import 'jquery/dist/jquery.min.js';
import './App.css';
import $ from 'jquery';
import Cookies from 'js-cookie';

window.bootstrap = require('bootstrap/dist/js/bootstrap.bundle.js');

class App extends Component {
  state = {
    cities: [],
    userRoles: [],
    editModal: {},
    currentCityId: {}
  };

  async componentDidMount() {
    const userResponse = await fetch('/api/user/roles');
    const response = await fetch('/api/cities');

    var modal = new window.bootstrap.Modal($("#editModal"), {
      keyboard: false
    })

    Promise.all([response.json(), userResponse.json()]).then((results) => {
      this.setState({cities: results[0], userRoles:results[1], editModal: modal});
    })
  }
  
  async deleteCity(id) {
    fetch('/api/cities/' + id, {
      method: 'DELETE',
      headers: new Headers({"X-XSRF-TOKEN": Cookies.get('XSRF-TOKEN')}),
    }).then((e) => {
      this.handleErrorResponse(e)
      this.hideModal()
      this.search()
    })
  }

  async updateCity() {
    const id = this.state.currentCityId
    const city = this.state.cities.find(item => item.id === id);
    const name = $("#editNameInput").val();
    const link = $("#editLinkInput").val();

    if (!this.isEditFormValid(name, link)) {
      return
    }

    if (city !== undefined) {
      fetch('/api/cities', {
        method: 'PUT',
        headers: new Headers({'content-type': 'application/json', "X-XSRF-TOKEN": Cookies.get('XSRF-TOKEN')}),
        body: JSON.stringify({
          'id': city.id,
          'name': name,
          'imageLink': link
        })
      }).then((e) => {
        this.handleErrorResponse(e)
        this.hideModal()
        this.search()
      })
    }
  }

  showModal(id) {
    var city = this.state.cities.find(item => item.id === id);
    this.setState({currentCityId: id});
    if (city !== undefined) {
      $("#editNameInput").val(city.name)
      $("#editLinkInput").val(city.imageLink)
      this.state.editModal.show();
    }
  }

  hideModal() {
    this.state.editModal.hide();
  }

  async search() {
    const search = $("#search").val()

    const response = await fetch(`/api/cities?searchText=${encodeURIComponent(search)}`);
    const body = await response.json();

    var modal = new window.bootstrap.Modal($("#editModal"), {
      keyboard: false
    })
    this.setState({cities: body, editModal: modal});
  }

  isEditFormValid(name, link) {
    if (name.length < 3) {
      alert("Name is too short")
      return false;
    }
    if (!link.startsWith("http")) {
      alert("Invalid link")
      return false;
    }
    return true;
  }

  async handleErrorResponse(response) {
    const responseBody = await response.json();
    if (!response.ok) {
      alert(responseBody.error)
    }
  }

  render() {
    const {cities} = this.state;
    return (
        <div className="App">
          {/*<header className="App-header">*/}
            <div className="App-intro">
              <h1>Cities</h1>
              <input id="search" onChange={() => this.search()} type="text" placeholder="Search a City..."/>
              <a id="logoutButton" href="/logout" className="btn btn-primary">Logout</a>
              <table className="">
                <tr>
                  <th>Name</th>
                  <th>Photo</th>
                  {this.state.userRoles.includes("ROLE_ADMIN") && <th className="tableButton">Edit</th> }
                  {this.state.userRoles.includes("ROLE_ADMIN") && <th className="tableButton">Delete</th> }
                </tr>
                {cities.map(city =>
                    <tr key={city.id}>
                      <td><h3 style={{textAlign: 'center'}}>{city.name}</h3></td>
                      <td><img className="tableImage" style={{height: 200}} src={city.imageLink} alt={'Not found'}/></td>
                      {this.state.userRoles.includes("ROLE_ADMIN") && <td><button className={'btn btn-primary'} onClick={() => this.showModal(city.id)}>Edit</button></td>}
                      {this.state.userRoles.includes("ROLE_ADMIN") && <td><button className={'btn btn-danger'} onClick={() => this.deleteCity(city.id)}>Delete</button></td>}
                    </tr>
                )}
              </table>
            </div>
          {/*</header>*/}
          <div id="editModal" className="modal" tabIndex="1000">
            <div className="modal-dialog modal-lg">
              <div className="modal-content">
                <div className="modal-header">
                  <h5 className="modal-title">Edit City</h5>
                  <button type="button" className="btn-close" onClick={() => this.hideModal()}  data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div className="modal-body form-group">
                  <label htmlFor="">City Name</label>
                  <br/>
                  <input className="input-lg col-md-6" id="editNameInput" type="text"/>
                  <br/>
                  <label htmlFor="">Image Link</label>
                  <br/>
                  <input className="col-md-8" id="editLinkInput" type="text"/>
                </div>
                <div className="modal-footer">
                  <button type="button" className="btn btn-secondary" onClick={() => this.hideModal()} >Close</button>
                  <button type="button" className="btn btn-primary" onClick={() => this.updateCity()}>Save changes
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
    );
  }
}

export default App;