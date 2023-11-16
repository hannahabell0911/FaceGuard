import React from "react";
import { Container, Row, Col, Form, Button } from "react-bootstrap";

const LoginPage = () => {
  return (
    <Container fluid>
      <Row>
        <Col md={6} className="d-none d-md-block bg-image"></Col>
        <Col md={6} className="login-section-wrapper">
          <div className="brand-wrapper">
            <img src="logo.png" alt="logo" className="logo" />
          </div>
          <div className="login-wrapper my-auto">
            <h1 className="login-title">Log in</h1>
            <Form>
              <Form.Group>
                <Form.Label>Email</Form.Label>
                <Form.Control type="email" placeholder="email@example.com" />
              </Form.Group>
              <Form.Group>
                <Form.Label>Password</Form.Label>
                <Form.Control
                  type="password"
                  placeholder="Enter your password"
                />
              </Form.Group>
              <Form.Check
                label="Remember me"
                style={{ marginBottom: "10px" }}
              />
              <Button variant="primary" type="submit" className="login-btn">
                Login
              </Button>
            </Form>
            <a href="#!" className="forgot-password-link">
              Forgot password?
            </a>
            <p className="login-wrapper-footer-text">
              Don't have an account?{" "}
              <a href="#!" className="text-reset">
                Register here
              </a>
            </p>
          </div>
        </Col>
      </Row>
    </Container>
  );
};

export default LoginPage;
