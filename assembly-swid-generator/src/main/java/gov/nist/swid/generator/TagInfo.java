/**
 * Portions of this software was developed by employees of the National Institute
 * of Standards and Technology (NIST), an agency of the Federal Government.
 * Pursuant to title 17 United States Code Section 105, works of NIST employees are
 * not subject to copyright protection in the United States and are considered to
 * be in the public domain. Permission to freely use, copy, modify, and distribute
 * this software and its documentation without fee is hereby granted, provided that
 * this notice and disclaimer of warranty appears in all copies.
 *
 * THE SOFTWARE IS PROVIDED 'AS IS' WITHOUT ANY WARRANTY OF ANY KIND, EITHER
 * EXPRESSED, IMPLIED, OR STATUTORY, INCLUDING, BUT NOT LIMITED TO, ANY WARRANTY
 * THAT THE SOFTWARE WILL CONFORM TO SPECIFICATIONS, ANY IMPLIED WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE, AND FREEDOM FROM
 * INFRINGEMENT, AND ANY WARRANTY THAT THE DOCUMENTATION WILL CONFORM TO THE
 * SOFTWARE, OR ANY WARRANTY THAT THE SOFTWARE WILL BE ERROR FREE. IN NO EVENT
 * SHALL NIST BE LIABLE FOR ANY DAMAGES, INCLUDING, BUT NOT LIMITED TO, DIRECT,
 * INDIRECT, SPECIAL OR CONSEQUENTIAL DAMAGES, ARISING OUT OF, RESULTING FROM, OR
 * IN ANY WAY CONNECTED WITH THIS SOFTWARE, WHETHER OR NOT BASED UPON WARRANTY,
 * CONTRACT, TORT, OR OTHERWISE, WHETHER OR NOT INJURY WAS SUSTAINED BY PERSONS OR
 * PROPERTY OR OTHERWISE, AND WHETHER OR NOT LOSS WAS SUSTAINED FROM, OR AROSE OUT
 * OF THE RESULTS OF, OR USE OF, THE SOFTWARE OR SERVICES PROVIDED HEREUNDER.
 */

package gov.nist.swid.generator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

public class TagInfo {
    private static final Properties DEFAULT_PROPERTIES;
    public static final String PROJECT_ARTIFACT_ID_PROPERTY_KEY = "artifact-id";
    public static final String PROJECT_GROUP_ID_PROPERTY_KEY = "group-id";
    public static final String PROJECT_VERSION_PROPERTY_KEY = "version";
    public static final String RESOURCE_DIGEST_PROPERTY_KEY = "resource-digest";
    public static final String TAG_ID_PROPERTY_KEY = "tag-id";
    public static final String TAG_VERISON_PROPERTY_KEY = "tag-version";

    public static final String TAG_VERISON_PROPERTY_DEFAULT = "0";

    static {
        DEFAULT_PROPERTIES = new Properties();
        DEFAULT_PROPERTIES.setProperty(TAG_VERISON_PROPERTY_KEY, TAG_VERISON_PROPERTY_DEFAULT);
    }

    private final File propertyFile;
    private final Properties properties;
    private boolean modified;

    /**
     * Construct the tag information from the information contained in the provided property file.
     * 
     * @param propertyFile
     *            the path to a property file containing tag information
     * @throws IOException
     *             if an error occurs while reading the property file
     */
    public TagInfo(File propertyFile) throws IOException {
        this.propertyFile = propertyFile;
        this.properties = new Properties(DEFAULT_PROPERTIES);

        loadProperties();
        setModified(false);
    }

    private boolean loadProperties() throws IOException {
        boolean retval;
        try (FileReader reader = new FileReader(getPropertyFile())) {
            properties.load(reader);
            retval = true;
        } catch (FileNotFoundException e) {
            // do nothing (except return false)
            retval = false;
        }
        return retval;
    }

    /**
     * Save the current properties to the property file.
     * 
     * @throws IOException
     *             if an error occurs while writing the property file
     */
    public void saveProperties() throws IOException {
        try (FileWriter writer = new FileWriter(getPropertyFile())) {
            this.properties.store(writer, "Auto generated by the class: " + getClass().getName() + ". Do not edit.");
        }
        setModified(false);
    }

    public boolean isModified() {
        return modified;
    }

    protected void setModified(boolean modified) {
        this.modified = modified;
    }

    public File getPropertyFile() {
        return propertyFile;
    }

    public String getProjectArtifactId() {
        return properties.getProperty(PROJECT_ARTIFACT_ID_PROPERTY_KEY);
    }

    protected Properties getProperties() {
        return properties;
    }

    /**
     * Sets the tagged project's artifactId to the provided identifier.
     * 
     * @param artifactId
     *            the artifact identifier for the project
     */
    public void setProjectArtifactId(String artifactId) {
        Util.requireNonEmpty(artifactId, "artifactId");
        this.properties.setProperty(PROJECT_ARTIFACT_ID_PROPERTY_KEY, artifactId);
        setModified(true);
    }

    public String getProjectGroupId() {
        return properties.getProperty(PROJECT_GROUP_ID_PROPERTY_KEY);
    }

    /**
     * Sets the tagged project's groupId to the provided identifier.
     * 
     * @param groupId
     *            the group identifier for the project
     */
    public void setProjectGroupId(String groupId) {
        Util.requireNonEmpty(groupId, "groupId");
        this.properties.setProperty(PROJECT_GROUP_ID_PROPERTY_KEY, groupId);
        setModified(true);
    }

    public String getProjectVersion() {
        return properties.getProperty(PROJECT_VERSION_PROPERTY_KEY);
    }

    /**
     * Sets the tagged project's version to the provided value.
     * 
     * @param version
     *            the value
     */
    public void setProjectVersion(String version) {
        Util.requireNonEmpty(version, "version");
        this.properties.setProperty(PROJECT_VERSION_PROPERTY_KEY, version);
        setModified(true);
    }

    public String getResourceDigest() {
        return properties.getProperty(RESOURCE_DIGEST_PROPERTY_KEY);
    }

    /**
     * Sets the resource digest to the provided value. This value is expected to be calculated by a
     * hash function that digests every resource associated with a tag to calculate a combined
     * digest value.
     * 
     * @param resourceDigest
     *            the digest value
     */
    public void setResourceDigest(String resourceDigest) {
        Util.requireNonEmpty(resourceDigest, "resourceDigest");
        this.properties.setProperty(RESOURCE_DIGEST_PROPERTY_KEY, resourceDigest);
        setModified(true);
    }

    public String getTagId() {
        return properties.getProperty(TAG_ID_PROPERTY_KEY);
    }

    /**
     * Sets the tagged project's identifer to the provided value.
     * 
     * @param tagId
     *            the value
     */
    public void setTagId(String tagId) {
        Util.requireNonEmpty(tagId, "tagId");
        this.properties.setProperty(TAG_ID_PROPERTY_KEY, tagId);
        setModified(true);
    }

    /**
     * Retrieves the tag's version.
     * 
     * @return the version value
     */
    public int getTagVersion() {
        String property = properties.getProperty(TAG_VERISON_PROPERTY_KEY, TAG_VERISON_PROPERTY_DEFAULT);
        return Integer.parseInt(property);
    }

    public void setTagVersion(String tagVersion) throws NumberFormatException {
        setTagVersion(Integer.parseInt(tagVersion));
    }

    /**
     * Sets the tag's version to the provided value.
     * 
     * @param tagVersion
     *            the value
     */
    public void setTagVersion(int tagVersion) {
        if (tagVersion < 0) {
            throw new IllegalArgumentException("tagVersion must be a non-negative integer value");
        }
        this.properties.setProperty(TAG_VERISON_PROPERTY_KEY, Integer.toString(tagVersion));
        setModified(true);
    }
}