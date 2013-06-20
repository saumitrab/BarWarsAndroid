package com.example.barwars.models;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.example.barwars.MainActivity;

import android.provider.Settings.Secure;

public class User {
	
	String UUID;
	String displayName;
	String profileImage;
	
	long solvedChallengesCount;
	long submittedChallengesCount;
	long points;
	
	//Challenge localSolved[];
	//Challenge localSubmitted[];
	Challenge unsolvedChallenges[];
	Challenge challengeToSubmit;

	public User() {
		
	}

	public String getUUID() {
		
		String UUID;
		String android_id = Secure.getString(null, Secure.ANDROID_ID);
		
		UUID = sha1(android_id + "barwars");
		return UUID;
	}

	public void setUUID(String uUID) {
		UUID = uUID;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public long getSolvedChallengesCount() {
		return solvedChallengesCount;
	}

	public void setSolvedChallengesCount(long solvedChallengesCount) {
		this.solvedChallengesCount = solvedChallengesCount;
	}

	public long getSubmittedChallengesCount() {
		return submittedChallengesCount;
	}

	public void setSubmittedChallengesCount(long submittedChallengesCount) {
		this.submittedChallengesCount = submittedChallengesCount;
	}

	public long getPoints() {
		return points;
	}

	public void setPoints(long points) {
		this.points = points;
	}

	public Challenge[] getUnsolvedChallenges() {
		return unsolvedChallenges;
	}

	public void setUnsolvedChallenges(Challenge[] unsolvedChallenges) {
		this.unsolvedChallenges = unsolvedChallenges;
	}

	public Challenge getChallengeToSubmit() {
		return challengeToSubmit;
	}

	public void setChallengeToSubmit(Challenge challengeToSubmit) {
		this.challengeToSubmit = challengeToSubmit;
	}
	
	

	public String sha1(String s) {
        MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        digest.reset();
        byte[] data = digest.digest(s.getBytes());
        return String.format("%0" + (data.length*2) + "X", new BigInteger(1, data));
	}
	// see http://androidsnippets.com/sha-1-hash-function
}
