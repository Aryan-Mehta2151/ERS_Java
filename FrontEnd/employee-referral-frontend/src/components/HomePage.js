import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import '../Styles/HomePage.css';

const HomePage = () => {
  const navigate = useNavigate();
  const [currentSlide, setCurrentSlide] = useState(0);

  const slides = [
    {
      image: 'https://images.unsplash.com/photo-1522071820081-009f0129c71c?ixlib=rb-4.0.3',
      title: 'Find Your Dream Job',
      description: 'Connect with top companies and opportunities'
    },
    {
      image: 'https://images.unsplash.com/photo-1573496359142-b8d87734a5a2?ixlib=rb-4.0.3',
      title: 'Hire Top Talent',
      description: 'Access a pool of qualified candidates'
    },
    {
      image: 'https://images.unsplash.com/photo-1551434678-e076c223a692?ixlib=rb-4.0.3',
      title: 'Grow Your Career',
      description: 'Take the next step in your professional journey'
    }
  ];

  const nextSlide = () => {
    setCurrentSlide((prev) => (prev + 1) % slides.length);
  };

  const prevSlide = () => {
    setCurrentSlide((prev) => (prev - 1 + slides.length) % slides.length);
  };

  useEffect(() => {
    const interval = setInterval(nextSlide, 5000); // Auto-slide every 5s
    return () => clearInterval(interval); // Clear on unmount
  }, []);

  return (
    <div className="home-container">
      <div className="background-overlay"></div>

      {/* Carousel Section */}
      <div className="carousel-container">
        <div className="carousel">
          {slides.map((slide, index) => (
            <div
              key={index}
              className={`slide ${index === currentSlide ? 'active' : ''}`}
              style={{
                backgroundImage: `linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)), url(${slide.image})`
              }}
            >
              <div className="slide-content">
                <h1>{slide.title}</h1>
                <p>{slide.description}</p>
              </div>
            </div>
          ))}
        </div>
        <button className="carousel-btn prev" onClick={prevSlide}>❮</button>
        <button className="carousel-btn next" onClick={nextSlide}>❯</button>
      </div>

      {/* Website Features Section */}
      <div className="features-section">
        <h2>Why Use Our Platform?</h2>
        <div className="features-grid">
          <div className="feature-box">
            <h3>AI-Powered Resume Parsing</h3>
            <p>Upload resumes and let our AI extract skills, experience, and more automatically.</p>
          </div>
          <div className="feature-box">
            <h3>Real-Time Chat</h3>
            <p>Connect instantly with recruiters and applicants using real-time messaging.</p>
          </div>
          <div className="feature-box">
            <h3>Smart Job Recommendations</h3>
            <p>Our system suggests jobs based on your profile, skills, and interests.</p>
          </div>
          <div className="feature-box">
            <h3>Role-Based Access</h3>
            <p>HRs, Employees, and Applicants all get tailored access and dashboards.</p>
          </div>
        </div>
      </div>

      {/* Call to Action */}
      <div className="cta-section">
        <h2>Ready to Get Started?</h2>
        <button className="get-started-btn" onClick={() => navigate('/login')}>
          Get Started
        </button>
      </div>

      {/* Footer */}
      <footer className="footer">
        <p>© {new Date().getFullYear()} Employee Referral System | All rights reserved.</p>
        <p>Made with ❤️ by Aryan Mehta</p>
      </footer>
    </div>
  );
};

export default HomePage;
