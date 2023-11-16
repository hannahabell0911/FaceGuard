import React from "react";
import { Container, Row, Col, Form, Button, Card } from "react-bootstrap";

const LoginPage = () => {
  return (
    <div className="login-background">
      <Container className="min-vh-100 d-flex align-items-center justify-content-center">
        <Row className="justify-content-center">
          <Col lg={5} md={7} sm={9} className="login-col">
            <Card className="login-card">
              <Card.Body>
                <div className="text-center mb-4">
                  <img src="logo.png" alt="logo" className="logo" />
                </div>
                <Form>
                  <Form.Group className="mb-3">
                    <Form.Label>Email address</Form.Label>
                    <Form.Control
                      type="email"
                      placeholder="Enter email"
                      required
                    />
                  </Form.Group>
                  <Form.Group className="mb-3">
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                      type="password"
                      placeholder="Password"
                      required
                    />
                  </Form.Group>
                  <Button variant="primary" type="submit" className="w-100">
                    Login
                  </Button>
                </Form>
                <div className="text-center mt-3">
                  <a href="#!" className="forgot-password-link">
                    Forgot password?
                  </a>
                </div>
                <div className="text-center mt-2">
                  <span className="login-wrapper-footer-text">
                    Don't have an account?
                  </span>
                  <a href="#!" className="text-reset">
                    Register here
                  </a>
                </div>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
    </div>
  );
};

export default LoginPage;
